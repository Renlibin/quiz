package cn.clubox.quiz.web.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.clubox.quiz.service.api.QuizManager;
import cn.clubox.quiz.service.api.auth.AccountProvisionService;
import cn.clubox.quiz.service.api.auth.OAuth2AccessToken;
import cn.clubox.quiz.service.api.auth.OAuth2Authenticator;
import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;
import cn.clubox.quiz.service.api.model.QuizExtension;
import cn.clubox.quiz.service.api.model.QuizExtension.QUIZ_DOABLE_ACTION;
import cn.clubox.quiz.service.api.payment.QuizPaymentService;
import cn.clubox.quiz.service.impl.auth.DatabaseUserDetailsService.User;
import cn.clubox.quiz.web.utils.OAuthConfig;
import cn.clubox.quiz.web.utils.QuizOrder;
import cn.clubox.quiz.web.utils.ThreadSafeLocalCache;

@Controller
@RequestMapping("/quiz")
public class PaymentController {

	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
	
	@Autowired
	private QuizManager quizManager;
	@Autowired
	private OAuth2Authenticator oAuth2Authenticator;
	@Autowired
	private QuizPaymentService paymentService;
	@Autowired
	private AccountProvisionService accountProvisionService;

	private ThreadSafeLocalCache<Integer,QuizOrder> cache;
	
	{
		cache = new ThreadSafeLocalCache<>();
	}
	
	@GetMapping("{quizType}/buynow")
	public String buyNow(@AuthenticationPrincipal User user, @PathVariable String quizType, Map<String, Object> model){
		
		logger.info("User {} is going to buy quiz {}", user.getId(), quizType);
		
		if(this.verifyQuizType(quizType) == false){
			logger.error("The quiz type {} is not exist", quizType);
			return "404";
		}
		
		QuizExtension targetQuizExtension = quizManager.retrieveQuizByType(user.getId(), true, true,QUIZ_DOABLE_ACTION.PAYNOW, quizType);
		List<QuizExtension> quizExtensionList = quizManager.retrieveAllQuiz(user.getId(),true, false, null);
		
		//To exclusive the target quiz
		for(int i=0; i < quizExtensionList.size(); i++){
			if(quizExtensionList.get(i).getQuiz().getId() == targetQuizExtension.getQuiz().getId()){
				quizExtensionList.remove(i);
			}
		}
		
		model.put("quizExtension", targetQuizExtension);
		model.put("quizExtensionList",quizExtensionList);
		
		return "buynow";
	}
	
	@GetMapping("/prePayment")
	public String prePayment(@AuthenticationPrincipal User user, @RequestParam("code") String code, Map<String,Object> model){
		
		if(logger.isDebugEnabled()){
			logger.debug("The AuthenticationPrincipal user id is {}", user.getId());
			logger.debug("The authorization code is {}", code);
		}
		
		//获取用户和公众号相关的openid
		OAuth2AccessToken token = oAuth2Authenticator.acquireAccessToken(code, OAuthConfig.GZ_APPPID,OAuthConfig.GZ_SECRET);
		
		if(logger.isDebugEnabled()){
			logger.debug("The access token is {}", token.toString());
		}
		
		//This step is not necessary
		Integer userId = accountProvisionService.retrieveUserIdByFederationId(token.getUnionid());
		
		//Get quiz type which save at previous step
		String quizType = cache.get(userId).getQuizType();
		
		if(logger.isDebugEnabled()){
			logger.debug("The order is going to be done via quiz {} and openid {}", quizType, token.getOpenid());
		}
		
		Map<String, String> result = new HashMap<>();
		try {
			result = paymentService.prePayment(token.getOpenid(), QUIZ_TYPE.valueOf(quizType));
//			{nonce_str=FQzVsNgiiuOIDpcS, 
//			device_info=WEB, 
//			appid=wxcd11f3f8760e5e43, 
//			sign=4644FA2C2749C6A481E4C893FF137A36, 
//			trade_type=JSAPI, return_msg=OK, result_code=SUCCESS, 
//			mch_id=1483723782, return_code=SUCCESS, 
//			prepay_id=wx2017112313414581927565090495456347}
			
		} catch (Exception e) {
			logger.error("{}", e.getMessage());
		}
//		result.put("timestamp", String.valueOf(new Date().getTime()));
		model.put("paymentParam", result);
		model.put("quizType", quizType);
		
		return "paynow";
	}
	
	@GetMapping("{quizType}/paynow")
	public String payNow(@AuthenticationPrincipal User user, @PathVariable String quizType, Map<String,Object> model){
		
		if(this.verifyQuizType(quizType) == false){
			logger.error("The quiz type {} is not exist", quizType);
			return "404";
		}
//		QuizExtension quizExtension = quizManager.retrieveQuizByType(user.getId(), true, false, QUIZ_DOABLE_ACTION.PAYMENT, quizType);
//		model.put("quizExtension", quizExtension);

		try {
			//Put the quiz order into local cache
			cache.put(user.getId(), new  QuizOrder(user.getId(),quizType));
			
			String codeAcquireUri = oAuth2Authenticator.acquireAuthorizationCode(OAuthConfig.GZ_APPPID,
						OAuthConfig.BASE_SCOPE, "http://www.rankbox.wang/rb/quiz/prePayment");
			return "redirect:".concat(codeAcquireUri);
		} catch (UnsupportedEncodingException e) {
			logger.error("Could not acquired authorization code due to exception {}", e.getMessage());
		}
		
		return "paynow";
	}
	
	@PostMapping("/payment")
	public String prePayment(@AuthenticationPrincipal User user, Map<String, Object> model){
		
//		Map<String, String> result = new HashMap<>();
//		try {
//			result = paymentService.prePayment(userOpenidMap.get(user.getId()), "", BigDecimal.ZERO);
//		} catch (Exception e) {
//			logger.error("{}", e.getMessage());
//		}
//		
//		model.put("paymentParam", result);
		
		return "payment";
	}
	
	@GetMapping(path = "/wxpay/notify", produces = {MediaType.APPLICATION_XML_VALUE})
	public String wxpayNotify(HttpServletRequest request){
		
		if(logger.isDebugEnabled()){
			logger.debug("<<<<<< Notification of wechat payment >>>>>>");
		}
		
		try {
            InputStream inStream = request.getInputStream();
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
                //将流转换成字符串
                String result = new String(outStream.toByteArray(), "UTF-8");
                //将字符串解析成XML
                Document doc = DocumentHelper.parseText(result);
                
                //将XML格式转化成MAP格式数据
//                Map<String, Object> resultMap = XmlMapHandle.Dom2Map(doc);
                
                if(logger.isDebugEnabled()){
                	logger.debug("The result is {} ", doc.toString());
                }
                //To persist the transaction into DB
            }
            //通知微信支付系统接收到信息
        return "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>";
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //如果失败返回错误，微信会再次发送支付信息
        return "fail";
	}
	
	public static Map<String, String> xmlToMap(String strXML) throws Exception {
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
	
	private boolean verifyQuizType(String quizType){
		if(QUIZ_TYPE.getByValue(quizType) != null){
			return true;
		}
		return false;
	}
}
