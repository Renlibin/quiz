package cn.clubox.quiz.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import cn.clubox.quiz.service.api.QuizManager;
import cn.clubox.quiz.service.api.payment.QuizPaymentService;
import cn.clubox.quiz.service.impl.auth.DatabaseUserDetailsService.User;

@Controller
public class QuizDecoratorController implements InitializingBean {
	
	private static Logger logger = LoggerFactory.getLogger(QuizDecoratorController.class);

	private RestTemplate restTemplate;
	
	@Autowired
	private QuizManager quizManager;
	
	@Autowired
	private QuizPaymentService paymentService;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		if(restTemplate == null){
			restTemplate = new RestTemplate();
		}
	}
	
	@GetMapping(path ="/quiz/proxy")
	public String retrievingQuiz(@AuthenticationPrincipal User user, @RequestParam("dest") String dest, Model model){
		
		if(logger.isDebugEnabled()){
			logger.debug("QuizDelegatorController.retrievingQuiz -> The real quiz {} is going to be retrieved", dest);
		}
		
		Integer userId = user.getId();
		Integer quizId = quizManager.retrieveQuizIdBySrc(dest);
		
		if(logger.isDebugEnabled()){
			logger.debug("QuizDecoratorController.retrievingQuiz -> The quiz id is {}", quizId);
		}
		
		if(paymentService.isPaid(userId, quizId) == false){
			return "redirect:/quiz/payment/proxy?dest=".concat(dest);
		}
		
//		String result = restTemplate.getForObject(dest, String.class);
//		if(logger.isDebugEnabled()){
//			logger.debug("QuizDelegatorController.retrievingQuiz -> The result is {}", result);
//		}
		
//		if(dest != null){
//			return "redirect:/quiz/payment/proxy?dest=".concat("http://ce.rankbox.wang/handler/jqemed.ashx?activity=19102123");
//		}
		
		model.addAttribute("src",dest);
		return "quiz_decorator";
		
	}
}
