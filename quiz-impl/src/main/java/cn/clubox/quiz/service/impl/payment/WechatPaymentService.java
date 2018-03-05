package cn.clubox.quiz.service.impl.payment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;

import cn.clubox.quiz.jooq.domain.tables.pojos.UserPayment;
import cn.clubox.quiz.service.api.QuizOrder;
import cn.clubox.quiz.service.api.QuizUrlEncodeDecodeService;
import cn.clubox.quiz.service.api.payment.QuizPaymentService;
import cn.clubox.quiz.service.api.util.Status;
import cn.clubox.quiz.service.impl.dao.QuizDaoExt;
import cn.clubox.quiz.service.impl.dao.QuizDaoExt.QuizExt;
import cn.clubox.quiz.service.impl.dao.QuizTradeNoCounterDaoExt;
import cn.clubox.quiz.service.impl.dao.UserPaymentDaoExt;

@Service
public class WechatPaymentService implements QuizPaymentService, InitializingBean {

	private static Logger logger = LoggerFactory.getLogger(WechatPaymentService.class);

	@Autowired
	private QuizTradeNoCounterDaoExt quizTradeNoCounterDao;

	@Autowired
	private QuizUrlEncodeDecodeService quizUrlEncodeDecodeService;

	@Autowired
	private QuizDaoExt quizDao;

	@Autowired
	private UserPaymentDaoExt userPaymentDao;
	
	@Value("${payment.notify.url}")
	private String notifyUrl;
	
	@Value("${spbill.create.ip}")
	private String spbillCreateIp;
	
	@Value("${item.description}")
	private String itemDescription;
	
	@Value("${payment.app.id}")
	private String appId;
	
	@Value("${payment.mch.id}")
	private String mchId;
	
	@Value("${payment.key}")
	private String paymentKey;
	
	private WXPay wxpay;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("WechatPaymentService.afterPropertiesSet -> itemDescription {}", itemDescription);

		if(Objects.isNull(notifyUrl) || Objects.isNull(spbillCreateIp) || Objects.isNull(itemDescription)
				|| Objects.isNull(appId) || Objects.isNull(mchId) || Objects.isNull(paymentKey)){
			throw new Exception("All propeties must not be null");
		}
		
		MyConfig config;
		try {
			config = new MyConfig(appId,mchId,paymentKey);
			
			if(logger.isDebugEnabled()){
				logger.debug("The MyConfig is {}", config);
			}
			
			wxpay = new WXPay(config);
		} catch (Exception e) {
			logger.error("Could not initialize WXpay due to exception {}", e.getMessage());
		}
	}

	@Override
	public boolean isPaid(Integer userId, int quizId) {
		return userPaymentDao.isQuizPaid(userId, quizId);
	}

	@Override
	public Map<String, String> prePayment(Integer userId, String openId, QuizOrder quizOrder) throws Exception {

		try {
			if(logger.isDebugEnabled()){
				logger.debug("WechatPaymentService.prePayment -> The quiz order is {}",quizOrder);
			}
			QuizExt quiz = null;
			if(quizOrder.getQuizType() != null && "external".equals(quizOrder.getQuizType()) == false){
				quiz = quizDao.fetchingQuizByType(quizOrder.getQuizType());
			}else{
				quiz = quizDao.fetchingQuizBySrc(quizUrlEncodeDecodeService.decode(quizOrder.getQuizSrc()));
			}
			
			if(logger.isDebugEnabled()){
				logger.debug("WechatPaymentService.prePayment -> The quiz is {}",quiz.toString());
			}
			
			if(Objects.isNull(quiz)){
				logger.error("WechatPaymentService.prePayment -> The quiz could not be found via quizOrder {}", quizOrder.toString());
				throw new Exception(String.format("WechatPaymentService.prePayment -> The quiz could not be found via quizOrder s%", quizOrder.toString()));
			}
			
			// 订单总金额，单位为分
			int fee = quiz.getPrice().movePointRight(2).intValue();
			String tradeNo = generateTradeNo();
			if (tradeNo == null) {
				throw new Exception("Could not generate trade NO !!!");
			}

			Map<String, String> data = new HashMap<String, String>();
			data.put("openid", openId);
			data.put("body", itemDescription); // 商品简单描述
			data.put("out_trade_no", tradeNo); // 商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@
												// ，且在同一个商户号下唯一
			data.put("device_info", "WEB");
			data.put("fee_type", "CNY");
			data.put("total_fee", String.valueOf(fee)); // 订单总金额，单位为分
			data.put("spbill_create_ip", spbillCreateIp); // APP和网页支付提交用户端ip
			data.put("notify_url", notifyUrl); // 异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数
			data.put("trade_type", "JSAPI"); // JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
			// data.put("product_id", "12"); trade_type 为 NATIVE 时必填

			// try {
			Map<String, String> resp = wxpay.unifiedOrder(data);
			if (logger.isDebugEnabled()) {
				logger.debug("The response of unified order is {}", resp);
			}

			// To persist payment order in DB
			UserPayment userPayment = new UserPayment();
			userPayment.setUserId(userId);
			userPayment.setAmount(quiz.getPrice());
			userPayment.setQuizId(quiz.getId());
			userPayment.setStored(new Timestamp(new Date().getTime()));
			userPayment.setStatus(Status.UNCOMPLETED.getValue());
			userPayment.setTradeNo(tradeNo);

			/**
			 * Integer id, Integer userId, Integer quizId, BigDecimal amount,
			 * Timestamp stored, String status, String tradeNo
			 */
			userPaymentDao.insert(userPayment);

			// "appId":"${paymentParam["appid"]}", //公众号名称，由商户传入
			// "timeStamp":"${paymentParam["timestamp"]}", //时间戳，自1970年以来的秒数
			// "nonceStr":"${paymentParam["nonce_str"]}", //随机串
			// "package":"prepay_id=${paymentParam["prepay_id"]}",
			// "signType":"MD5", //微信签名方式：
			// "paySign":"${paymentParam["sign"]}"//微信签名

			String appid = resp.get("appid");
			String nonce_str = UUID.randomUUID().toString().substring(0, 32);
			String timestamp = String.valueOf(new Date().getTime());
			String sign = md5Hex(appid, resp.get("prepay_id"), nonce_str, timestamp);

			if (logger.isDebugEnabled()) {
				logger.debug("The signed string is {}", sign);
			}

			Map<String, String> nr = new HashMap<>();
			nr.put("appid", appid);
			nr.put("prepay_id", resp.get("prepay_id"));
			nr.put("timestamp", timestamp);
			nr.put("sign", sign);
			nr.put("nonce_str", nonce_str);

			return nr;
		} catch (Exception e) {
			logger.error("due to exception {}", e.getMessage());
		}

		return new HashMap<>();
	}

	@Override
	public String updatePaymentTransactionId(InputStream inStream) {
		
		try{
			int _buffer_size = 1024;
			if (inStream != null) {
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				byte[] tempBytes = new byte[_buffer_size];
				int count = -1;
				while ((count = inStream.read(tempBytes, 0, _buffer_size)) != -1) {
					outStream.write(tempBytes, 0, count);
				}
				tempBytes = null;
				outStream.flush();
				// 将流转换成字符串
				String result = new String(outStream.toByteArray(), "UTF-8");
				// 将字符串解析成XML
				Document doc = DocumentHelper.parseText(result);
	
				// 将XML格式转化成MAP格式数据
				// Map<String, Object> resultMap = XmlMapHandle.Dom2Map(doc);
	
				if (logger.isDebugEnabled()) {
					logger.debug("The result is {} ", doc.asXML());
				}
	
				/**
				 * <?xml version="1.0" encoding="UTF-8"?>
				 * <xml><appid><![CDATA[wxcd11f3f8760e5e43]]></appid>
				 * <bank_type><![CDATA[CFT]]></bank_type>
				 * <cash_fee><![CDATA[1]]></cash_fee>
				 * <device_info><![CDATA[WEB]]></device_info>
				 * <fee_type><![CDATA[CNY]]></fee_type>
				 * <is_subscribe><![CDATA[Y]]></is_subscribe>
				 * <mch_id><![CDATA[1483723782]]></mch_id>
				 * <nonce_str><![CDATA[c42d3663699a49f183e2757a9bddbfce]]>
				 * </nonce_str>
				 * <openid><![CDATA[oBWOm0hAedI0Tfm3PDSDuqq9mo3w]]></openid>
				 * <out_trade_no><![CDATA[2017112710405300000005]]></out_trade_no>
				 * <result_code><![CDATA[SUCCESS]]></result_code>
				 * <return_code><![CDATA[SUCCESS]]></return_code>
				 * <sign><![CDATA[261420B363B20C5C87F8A486A1DCA799]]></sign>
				 * <time_end><![CDATA[20171127104057]]></time_end>
				 * <total_fee>1</total_fee>
				 * <trade_type><![CDATA[JSAPI]]></trade_type>
				 * <transaction_id><![CDATA[4200000040201711277348342397]]>
				 * </transaction_id> </xml>
				 */
	
				Map<String, String> resultMap = xmlToMap(doc.asXML());
				String tradeNo = resultMap.get("out_trade_no");
				String transactionId = resultMap.get("transaction_id");
	
				// To persist the transaction into DB
				int um = userPaymentDao.updateUserPaymentTransactionId(tradeNo, transactionId);
				// 通知微信支付系统接收到信息
				return um > 0 ? "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>" : "fail";
			}
		}catch(Exception e){
			logger.error("Could not update transaction id due to exception {}", e.getMessage());
		}
		// 如果失败返回错误，微信会再次发送支付信息
		return"fail";
	}

	@Override
	public boolean purchase(String userId, int quizId, BigDecimal price) {

		return false;
	}

	private String md5Hex(String appid, String prepay_id, String nonce_str, String timestamp)
			throws UnsupportedEncodingException {

		String singStr = "appId=".concat(appid).concat("&nonceStr=").concat(nonce_str).concat("&package=prepay_id=")
				.concat(prepay_id).concat("&signType=MD5&timeStamp=").concat(timestamp);

		if (logger.isDebugEnabled()) {
			logger.debug("The sign string is {}", singStr);
		}

		String signTemp = singStr.concat("&key=").concat(paymentKey); // 注：key为商户平台设置的密钥key

		return DigestUtils.md5Hex(signTemp.getBytes("UTF-8")).toUpperCase();
	}

	private String generateTradeNo() {

		if (logger.isDebugEnabled()) {
			logger.debug("The trade no is going to be generted");
		}
		Integer tradeNo;
		synchronized (this) {
			tradeNo = quizTradeNoCounterDao.fetchAndIncrementTradeNo();
		}

		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String formatDateTime = now.format(formatter);
		String tradeNoStr = formatDateTime.concat(String.format("%08d", tradeNo));

		logger.info("The trade NO. is {}", tradeNoStr);

		return tradeNoStr;
	}
	
	private Map<String, String> xmlToMap(String strXML) throws Exception {
		
        Map<String, String> data = new HashMap<String, String>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
        org.w3c.dom.Document doc = documentBuilder.parse(stream);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int idx=0; idx<nodeList.getLength(); ++idx) {
            Node node = nodeList.item(idx);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                data.put(element.getNodeName(), element.getTextContent());
            }
        }
        try {
            stream.close();
        }
        catch (Exception ex) {

        }
        return data;
    }

	public static class MyConfig implements WXPayConfig {

		// 撤销、退款申请API中调用
		private byte[] certData;
		private String appId;
		private String mchId;
		private String key;

		public MyConfig() throws Exception {
			// String certPath = "/path/to/apiclient_cert.p12";
			// File file = new File(certPath);
			// InputStream certStream = new FileInputStream(file);
			// this.certData = new byte[(int) file.length()];
			// certStream.read(this.certData);
			// certStream.close();
		}
		
		public MyConfig(String appId, String mchId, String key) throws Exception {
			this.appId = appId;
			this.mchId = mchId;
			this.key = key;
		}

		public String getAppID() {
//			return "wxcd11f3f8760e5e43";
			return appId;
		}

		public String getMchID() {
//			return "1483723782";
			return mchId;
		}

		public String getKey() {
			return key;
		}

		public InputStream getCertStream() {
			ByteArrayInputStream certBis = new ByteArrayInputStream(this.certData);
			return certBis;
		}

		public int getHttpConnectTimeoutMs() {
			return 8000;
		}

		public int getHttpReadTimeoutMs() {
			return 10000;
		}

		@Override
		public String toString() {
			return "MyConfig [certData=" + Arrays.toString(certData) + ", appId=" + appId + ", mchId=" + mchId
					+ ", key=" + key + "]";
		}
		
	}

	public static void main(String[] args) {

		// String s =
		// DigestUtils.md5Hex("appId=wxcd11f3f8760e5e43&nonceStr=c9b44aaa-6f68-46a6-b5d5-9e90fc87&package=prepay_id=wx2017112521530910caeec27b0904586752&signType=MD5&timeStamp=1511617989149");
		// System.out.println("Signed string is " + s.toUpperCase());
		
		if("abc".equals(null) == false){
			
		}

		LocalDateTime now = LocalDateTime.now();
		System.out.println("Before : " + now);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String formatDateTime = now.format(formatter);

		System.out.println("formatDateTime = " + formatDateTime);

		System.out.println(String.format("%05d", 110));

		BigDecimal bd = new BigDecimal(8.88);
		System.out.println("Price is " + bd.movePointRight(2).intValue());
	}

}
