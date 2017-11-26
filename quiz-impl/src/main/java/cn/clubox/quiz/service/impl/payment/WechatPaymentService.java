package cn.clubox.quiz.service.impl.payment;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.wxpay.sdk.WXPay;
import com.github.wxpay.sdk.WXPayConfig;

import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;
import cn.clubox.quiz.service.api.payment.QuizPaymentService;
import cn.clubox.quiz.service.impl.dao.QuizDaoExt;
import cn.clubox.quiz.service.impl.dao.QuizDaoExt.QuizExt;
import cn.clubox.quiz.service.impl.dao.QuizTradeNoCounterDaoExt;

@Service
public class WechatPaymentService implements QuizPaymentService {

	private static Logger logger = LoggerFactory.getLogger(WechatPaymentService.class);
	
	@Autowired
	private QuizTradeNoCounterDaoExt quizTradeNoCounterDao;
	
	@Autowired
	private QuizDaoExt quizDao;
	
	private WXPay wxpay;
	
	{
		MyConfig config;
		try {
			config = new MyConfig();
			wxpay = new WXPay(config);
		} catch (Exception e) {
			logger.error("Could not initialize WXpay due to exception {}",e.getMessage());
		}
	}
	
	
	@Override
	public boolean isPaid(String userId, int quizId) {
		return false;
	}

	@Override
	public Map<String,String> prePayment(String openId, QUIZ_TYPE quizType) throws Exception {
		
		QuizExt quiz = quizDao.fetchingQuizByType(quizType.value);

		String tradeNo;
		
		synchronized(this){
			tradeNo  = generateTradeNo();
		}
		
		if(tradeNo == null){
			throw new Exception("Could not generate trade NO !!!");
		}
		
        Map<String, String> data = new HashMap<String, String>();
        data.put("openid",openId);
        data.put("body", "瑞博测评-在线职业测评"); //商品简单描述
        data.put("out_trade_no", tradeNo);  //商户系统内部订单号，要求32个字符内，只能是数字、大小写字母_-|*@ ，且在同一个商户号下唯一
        data.put("device_info", "WEB");
        data.put("fee_type", "CNY");
        data.put("total_fee", "1"); //订单总金额，单位为分
        data.put("spbill_create_ip", "101.201.43.85"); //APP和网页支付提交用户端ip
        data.put("notify_url", "http://www.rankbox.wang/rb/quiz/wxpay/notify"); //异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数
        data.put("trade_type", "JSAPI");  //JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付
//      data.put("product_id", "12"); trade_type 为 NATIVE 时必填

        try {
            Map<String, String> resp = wxpay.unifiedOrder(data);
            if(logger.isDebugEnabled()){
            	logger.debug("The response of unified order is {}", resp);
            }
            
            //To persist payment order in DB
            
//            "appId":"${paymentParam["appid"]}",   //公众号名称，由商户传入     
//	           "timeStamp":"${paymentParam["timestamp"]}",       //时间戳，自1970年以来的秒数     
//	           "nonceStr":"${paymentParam["nonce_str"]}", //随机串     
//	           "package":"prepay_id=${paymentParam["prepay_id"]}",     
//	           "signType":"MD5",    //微信签名方式：     
//	           "paySign":"${paymentParam["sign"]}"//微信签名 
	        
            //stringA="appid=wxd930ea5d5a258f4f&body=test&device_info=1000&mch_id=10000100&nonce_str=ibuaiVcKdpRxkhJA"; 
            
            String appid = resp.get("appid");
            String nonce_str = UUID.randomUUID().toString().substring(0,32);
            String timestamp = String.valueOf(new Date().getTime());
            
            String singStr = "appId=".concat(resp.get("appid")).concat("&nonceStr=").concat(nonce_str)
            		.concat("&package=prepay_id=").concat(resp.get("prepay_id")).concat("&signType=MD5&timeStamp=")
            		.concat(timestamp);

            if(logger.isDebugEnabled()){
            	logger.debug("The sign string is {}", singStr);
            }
            
            String signTemp=singStr+"&key=ZhiyecepingZhiyeceping1234567890"; //注：key为商户平台设置的密钥key
//          sign=MD5(stringSignTemp).toUpperCase()="9A0A8659F005D6984697E2CA0A9CF3B7" //注：MD5签名方式
            
//            byte[] thedigest = MessageDigest.getInstance("MD5").digest(signTemp.getBytes("UTF-8"));
//            String sign = String.valueOf(thedigest);
            
            String sign = DigestUtils.md5Hex(signTemp.getBytes("UTF-8")).toUpperCase();
            
            if(logger.isDebugEnabled()){
            	logger.debug("The signed string is {}", sign);
            }
            
            Map<String,String> nr = new HashMap<>();
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
	public boolean purchase(String userId, int quizId, BigDecimal price) {
		
		return false;
	}
	
	private String sign(){
		
		return null;
	}
	
	private String generateTradeNo(){
		
		Integer tradeNo = quizTradeNoCounterDao.fetchingTradeNo();
		
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formatDateTime = now.format(formatter);
        String tradeNoStr = formatDateTime.concat(String.format("%08d", tradeNo));
        
        logger.info("The trade NO. is {}", tradeNoStr);
        
		return tradeNoStr;
	}
	
	
	public static class MyConfig implements WXPayConfig{

		//撤销、退款申请API中调用
	    private byte[] certData;

	    public MyConfig() throws Exception {
//	        String certPath = "/path/to/apiclient_cert.p12";
//	        File file = new File(certPath);
//	        InputStream certStream = new FileInputStream(file);
//	        this.certData = new byte[(int) file.length()];
//	        certStream.read(this.certData);
//	        certStream.close();
	    }

	    public String getAppID() {
	        return "wxcd11f3f8760e5e43";
	    }

	    public String getMchID() {
	        return "1483723782";
	    }

	    public String getKey() {
	        return "ZhiyecepingZhiyeceping1234567890";
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
	}
	
	public static void main(String[] args){
		
//		String s = DigestUtils.md5Hex("appId=wxcd11f3f8760e5e43&nonceStr=c9b44aaa-6f68-46a6-b5d5-9e90fc87&package=prepay_id=wx2017112521530910caeec27b0904586752&signType=MD5&timeStamp=1511617989149");
//		System.out.println("Signed string is " + s.toUpperCase());
		
		LocalDateTime now = LocalDateTime.now();
        System.out.println("Before : " + now);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formatDateTime = now.format(formatter);
        
        System.out.println("formatDateTime = " + formatDateTime);
        
        System.out.println(String.format("%05d", 110));
	}

}
