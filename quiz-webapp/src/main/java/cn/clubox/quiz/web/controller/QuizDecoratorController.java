package cn.clubox.quiz.web.controller;

import java.util.List;

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
import cn.clubox.quiz.service.api.model.Quiz;
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
		
//		if(dest != null){
//			return "redirect:/quiz/payment/proxy?dest=".concat("http://ce.rankbox.wang/handler/jqemed.ashx?activity=19102123");
//		}
		
		model.addAttribute("src",dest);
		return "quiz_decorator";
	}
	
	@GetMapping(path="/quiz/my")
	public String myQuiz(@AuthenticationPrincipal User user, Model model){
		
		if(logger.isDebugEnabled()){
			logger.debug("QuizDelegatorController.myQuiz -> My quizs are going to be retrieved");
		}
		
		int userId = user.getId();
		String nickname = user.getNickname();
		String portraitSrc = user.getPortraitSrc();
		
		List<Quiz> engagedQuizList = quizManager.retrievePaidExternalQuiz(userId);
		
		if(logger.isDebugEnabled()){
			logger.debug("{} engaged {} quizs", nickname, engagedQuizList == null ? 0 :engagedQuizList.size());
		}
		
		model.addAttribute("nickname",nickname);
		model.addAttribute("portraitSrc",portraitSrc);
		model.addAttribute("myQuizList", engagedQuizList);
		
		return "my_quiz";
	}
}