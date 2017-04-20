package cn.clubox.quiz.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import cn.clubox.quiz.service.api.QuizAnswerSheetProcessor;
import cn.clubox.quiz.service.api.QuizManager;
import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.Quiz;
import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;
import cn.clubox.quiz.service.api.model.QuizAnswerSheet;
import cn.clubox.quiz.service.api.model.QuizExtension;
import cn.clubox.quiz.service.impl.auth.DatabaseUserDetailsService.User;
import cn.clubox.quiz.web.utils.QuizAnswerSheetProcessorFactory;

@Controller
public class QuizController {

	private static final Logger logger = LoggerFactory.getLogger(QuizController.class);
	
	@Autowired
	private QuizAnswerSheetProcessorFactory quizAnswerSheetProcessorFactory;
	
	@Autowired
	private QuizManager quizManager;
	
	private Map<String, Quiz> quizMap;
	private Map<String, List<Question>> questionMap;
	
	@PostConstruct
	public void init() throws Exception {
		
		quizMap = quizManager.retrieveAllQuiz();
		questionMap = quizManager.retrieveAllQuizQuestion();
		
	}
	
	@GetMapping("quiz/home")
	public String showAllQuiz(@AuthenticationPrincipal User user, Map<String, Object> model){
		
		int userId = user.getId();
		List<Quiz> quizList = new ArrayList<>(quizMap.values());
		List<QuizExtension> quizExtensionList = quizManager.avilableActionDecision(userId, quizList.toArray(new Quiz[quizList.size()]));
		
		model.put("quizExtensionList", quizExtensionList);
		return "index";
	}
	
	@GetMapping("quiz/{quizType}/engagement")
	public String showQuiz(@AuthenticationPrincipal User user, @PathVariable String quizType, Map<String, Object> model){
		
		int userId = user.getId();
		
		if(QUIZ_TYPE.getByValue(quizType) == null){
			logger.error("The quiz type {} is not exist", quizType);
			return "404";
		}
		
		Quiz quiz = quizMap.get(quizType);
		List<Question> questionList = questionMap.get(quizType);
		
		if(quiz == null || questionList == null){
			logger.error("Quiz or quiz's questions could not be found!");
			return "error";
		}
		
		quiz.setQuestionList(questionList);
		
		logger.debug("Quiz question size is ========== {}" + quiz.getQuestionList().size());
		
		List<QuizExtension> quizExtensionList = quizManager.avilableActionDecision(userId, quiz);
		
		if(quizExtensionList == null || quizExtensionList.isEmpty()){
			logger.error("Avilable action of the quiz could not be decided!");
			return "error";
		}
		
		model.put("quiz", quiz);
//		model.put("zymAnswer", ZYM_ANSWER.values());
		
		return "zym";
	}
	
	@PostMapping("quiz/{quizType}/engagement")
	public String submitQuiz(@AuthenticationPrincipal User user, @PathVariable String quizType, 
			@ModelAttribute QuizAnswerSheet quizAnswerSheet, Map<String, Object> model){
		
		quizAnswerSheet.setUserId(user.getId());
		
		logger.debug("Duration is {}", quizAnswerSheet.getDuration());
		
		if(QUIZ_TYPE.getByValue(quizType) == null){
			logger.error("The quiz type {} is not exist", quizType);
			return "404";
		}
		
		QuizAnswerSheetProcessor processor = quizAnswerSheetProcessorFactory.getProcessor(quizType);
		
		logger.info("Processor is {}", processor);
		
		if(processor == null){
			return "error";
		}
		
		processor.process(quizAnswerSheet);
		
		return "redirect:/quiz/result";
	}
	
	@GetMapping("quiz/{quizType}/buynow")
	public String buyNow(){
		
		return "buyNow";
	}
	
	@GetMapping("quiz/result")
	public String showResult(){
		
		return "result";
	}
	
	@GetMapping("quiz/myQuiz")
	public String myQuiz(){
		
		
		return "myQuiz";
	}

}
