package cn.clubox.quiz.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.clubox.quiz.service.api.QuizAnswerSheetProcessor;
import cn.clubox.quiz.service.api.QuizManager;

import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;
import cn.clubox.quiz.service.api.model.QuizAnswerSheet;
import cn.clubox.quiz.service.api.model.QuizExtension;
import cn.clubox.quiz.service.api.model.QuizExtension.QUIZ_DOABLE_ACTION;
import cn.clubox.quiz.service.impl.auth.DatabaseUserDetailsService.User;
import cn.clubox.quiz.web.utils.QuizAnswerSheetProcessorFactory;

@Controller
public class QuizController {

	private static final Logger logger = LoggerFactory.getLogger(QuizController.class);
	
	@Autowired
	private QuizManager quizManager;
	
	@Autowired
	private QuizAnswerSheetProcessorFactory quizAnswerSheetProcessorFactory;
	
	@GetMapping("quiz/refresh")
	public String refreshQuiz(){
		
		logger.info("Refreshing quizs and questions ...... ");
		return "redirect:/quiz/home";
	}
	
	@GetMapping("quiz/index")
	public String showAllQuiz(@AuthenticationPrincipal User user, Map<String, Object> model){
		
		int userId = user.getId();
		List<QuizExtension> quizExtensionList = quizManager.retrieveAllQuiz(userId, true, false, null);
		
		model.put("quizExtensionList", quizExtensionList);
		return "index";
	}
	
	@GetMapping("quiz/{quizType}/engagement")
	public String showQuiz(@AuthenticationPrincipal User user, @PathVariable String quizType, Map<String, Object> model){
		
		int userId = user.getId();
		
		if(this.verifyQuizType(quizType) == false){
			logger.error("The quiz type {} is not exist", quizType);
			return "404";
		}
		
		//Need to check whether the user has payed for the quiz
		if(quizManager.hasPrivilige(userId,quizType) == false){
			"redirect:/quiz".concat(quizType).concat("/buynow");
		}
		
		QuizExtension quizExtension = quizManager.retrieveQuizByType(userId, false, true, null, quizType);
		
		model.put("quizExtension", quizExtension);
		return "quiz_engagement";
	}
	
	@PostMapping("quiz/{quizType}/engagement")
	public String submitQuiz(@AuthenticationPrincipal User user, @PathVariable String quizType, 
			@ModelAttribute QuizAnswerSheet quizAnswerSheet, Map<String, Object> model){
		
		int userId = user.getId();
		quizAnswerSheet.setUserId(userId);
		
		if(this.verifyQuizType(quizType) == false){
			logger.error("The quiz type {} is not exist", quizType);
			return "404";
		}
		
		//Need to check whether the user has payed for the quiz
		if(quizManager.hasPrivilige(userId,quizType) == false){
			"redirect:/quiz".concat(quizType).concat("/buynow");
		}
		
		QuizAnswerSheetProcessor<?> processor = quizAnswerSheetProcessorFactory.getProcessor(quizType);
		
		logger.info("Processor is {}", processor);
		
		if(processor == null){
			return "error";
		}
		
		int engagementId = processor.process(quizAnswerSheet);
		
		return "redirect:/quiz/".concat(quizType).concat("/result?engagementId=").concat(String.valueOf(engagementId));
	}
	
	@GetMapping("quiz/{quizType}/buynow")
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
	
	@GetMapping("quiz/{quizType}/paynow")
	public String payNow(@AuthenticationPrincipal User user, @PathVariable String quizType, Map<String,Object> model){
		
		if(this.verifyQuizType(quizType) == false){
			logger.error("The quiz type {} is not exist", quizType);
			return "404";
		}
		QuizExtension quizExtension = quizManager.retrieveQuizByType(user.getId(), true, false, QUIZ_DOABLE_ACTION.PAYMENT, quizType);
		
		model.put("quizExtension", quizExtension);
		return "paynow";
	}
	
	@GetMapping("quiz/{quizType}/result")
	public String showResult(@AuthenticationPrincipal User user, @PathVariable String quizType, 
			@RequestParam(value="engagementId",required=false) Integer engagementId, Map<String, Object> model){
		
		Map<String,Integer> resultMap = new HashMap<>();
		
		if(engagementId != null && engagementId != 0){
			resultMap = quizManager.retrieveQuizEngagementResult(engagementId);
		}else{
			resultMap = quizManager.retrieveQuizEngagementResult(user.getId(), quizType);
		}
		
		model.put("resultMap", resultMap);
		return quizType.concat("_result");
	}
	
	@GetMapping("quiz/private/engaged")
	public String engagedQuiz(@AuthenticationPrincipal User user, Map<String,Object> model){
		
		int userId = user.getId();
		String nickname = user.getNickname();
		String portraitSrc = user.getPortraitSrc();
		
		List<QuizExtension> engagedQuizList = quizManager.retrieveEngagedQuiz(userId, true, false);
		
		if(logger.isDebugEnabled()){
			logger.debug("{} engaged {} quizs", nickname, engagedQuizList == null ? 0 :engagedQuizList.size());
		}
		
		model.put("nickname",nickname);
		model.put("portraitSrc",portraitSrc);
		model.put("engagedQuizList", engagedQuizList);
		return "private_quiz_engaged";
	}
	
	@GetMapping("quiz/private/undone")
	public String undoneQuiz(@AuthenticationPrincipal User user, Map<String,Object> model){
		
		int userId = user.getId();
		String nickname = user.getNickname();
		String portraitSrc = user.getPortraitSrc();
		List<QuizExtension> undoneQuizList = quizManager.retrieveUndoneQuiz(userId, true, false);
		
		logger.debug("undoneQuizList size is {}", undoneQuizList.size());
		
		model.put("nickname",nickname);
		model.put("portraitSrc",portraitSrc);
		model.put("undoneQuizList", undoneQuizList);
		return "private_quiz_undone";
	}
	
	private boolean verifyQuizType(String quizType){
		if(QUIZ_TYPE.getByValue(quizType) != null){
			return true;
		}
		return false;
	}
}
