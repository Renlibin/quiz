package cn.clubox.quiz.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.clubox.quiz.service.api.QuizManager;
import cn.clubox.quiz.service.api.QuizOrder;
import cn.clubox.quiz.service.api.auth.AccountProvisionService;
import cn.clubox.quiz.service.api.auth.OAuth2AccessToken;
import cn.clubox.quiz.service.api.auth.OAuth2Authenticator;
import cn.clubox.quiz.service.api.model.Quiz.QuizType;
import cn.clubox.quiz.service.api.model.QuizExtension;
import cn.clubox.quiz.service.api.model.QuizExtension.QUIZ_DOABLE_ACTION;
import cn.clubox.quiz.service.api.payment.QuizPaymentService;
import cn.clubox.quiz.service.impl.auth.DatabaseUserDetailsService.User;
import cn.clubox.quiz.web.utils.OAuthConfig;
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
//	@Autowired
//	private AccountProvisionService accountProvisionService;

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
	public String prePayment(@AuthenticationPrincipal User user, @RequestParam("code") String code, 
			@RequestParam("dest") String dest, Map<String,Object> model){
		
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
		//Do not know why there is no union id in the token.
		//Integer userId = accountProvisionService.retrieveUserIdByFederationId(token.getUnionid());
		
		Integer userId = user.getId();
		
		//Get quiz order which save at previous step
		QuizOrder quizOrder = cache.get(userId);
		
		if(logger.isDebugEnabled()){
			logger.debug("The order is going to be done via quiz {} and openid {}", 
					quizOrder.getQuizType() != null ? quizOrder.getQuizType() : quizOrder.getQuizSrc(), token.getOpenid());
		}
		
		Map<String, String> result = new HashMap<>();
		try {
			result = paymentService.prePayment(user.getId(),token.getOpenid(), quizOrder);
			String quizEngagementUrl;
			if(Objects.nonNull(dest)){
				quizEngagementUrl = String.format("http://www.rankbox.wang/rb/quiz/proxy?dest=%s", quizOrder.getQuizSrc());
			}else{
				quizEngagementUrl = String.format("http://www.rankbox.wang/rb/quiz/%s/engagement",quizOrder.getQuizType());
			}
			result.put("quiz_engagement_url", quizEngagementUrl);
//			{nonce_str=FQzVsNgiiuOIDpcS, 
//			device_info=WEB, 
//			appid=wxcd11f3f8760e5e43, 
//			sign=4644FA2C2749C6A481E4C893FF137A36, 
//			trade_type=JSAPI, return_msg=OK, result_code=SUCCESS, 
//			mch_id=1483723782, return_code=SUCCESS, 
//			prepay_id=wx2017112313414581927565090495456347}
			
		} catch (Exception e) {
			logger.error("Cound not do pre payment due to exception {}", e.getMessage());
		}
//		result.put("timestamp", String.valueOf(new Date().getTime()));
		model.put("paymentParam", result);
//		model.put("quizType", quizType);
		
		cache.remove(userId);
		
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
			cache.put(user.getId(), new  QuizOrder(user.getId(),quizType,null));
			
			String codeAcquireUri = oAuth2Authenticator.acquireAuthorizationCode(OAuthConfig.GZ_APPPID,
						OAuthConfig.BASE_SCOPE, "http://www.rankbox.wang/rb/quiz/prePayment");
			return "redirect:".concat(codeAcquireUri);
		} catch (UnsupportedEncodingException e) {
			logger.error("Could not acquired authorization code due to exception {}", e.getMessage());
		}
		
		return "paynow";
	}
	
	@GetMapping("/payment/proxy")
	public String paymentProxy(@AuthenticationPrincipal User user, @RequestParam("dest") String dest, Map<String,Object> model){
		
		if(logger.isDebugEnabled()){
			logger.debug("PaymentController.paymentProxy ->  {}", dest);
		}
//		QuizExtension quizExtension = quizManager.retrieveQuizByType(user.getId(), true, false, QUIZ_DOABLE_ACTION.PAYMENT, quizType);
//		model.put("quizExtension", quizExtension);

		try {
			//Put the quiz order into local cache
			// ****** Here is hard coding which need to be replaced by suitable mean ******
			cache.put(user.getId(), new  QuizOrder(user.getId(),null,dest));
			
			String codeAcquireUri = oAuth2Authenticator.acquireAuthorizationCode(OAuthConfig.GZ_APPPID,
						OAuthConfig.BASE_SCOPE, "http://www.rankbox.wang/rb/quiz/prePayment?dest=".concat(dest));
			return "redirect:".concat(codeAcquireUri);
		} catch (UnsupportedEncodingException e) {
			logger.error("Could not acquired authorization code due to exception {}", e.getMessage());
		}
		
		return "paynow";
	}
	
	@PostMapping("/payment")
	public String prePayment(@AuthenticationPrincipal User user, Map<String, Object> model){
		
		return "payment";
	}
	
	@PostMapping("/wxpay/notify")
	@ResponseBody
	public String wxpayNotify(HttpServletRequest request){
		
		if(logger.isDebugEnabled()){
			logger.debug("Notification of wechat payment");
		}
		
		try {
			String response = paymentService.updatePaymentTransactionId(request.getInputStream());
			if(logger.isDebugEnabled()){
				logger.debug("The response of wxPayNotify is {}", response);
			}
			return response;
		} catch (IOException e) {
			logger.error("Could not process wxPayNotify properly !!!");
			return "fail";
		}		
	}
	
	private boolean verifyQuizType(String quizType){
		if(QuizType.getByValue(quizType) != null){
			return true;
		}
		return false;
	}
}
