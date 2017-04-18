package cn.clubox.quiz.web.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import cn.clubox.quiz.service.api.QuizAnswerSheetProcessor;
import cn.clubox.quiz.service.api.QuizQuestionGenerator;
import cn.clubox.quiz.service.api.QuizQuestionGenerator.QuizQuestion;
import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.QuestionsModel;
import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;
import cn.clubox.quiz.web.utils.QuizAnswerSheetProcessorFactory;
import cn.clubox.quiz.web.utils.ZYM_ANSWER;

@Controller
public class QuizController {

	private static final Logger logger = LoggerFactory.getLogger(QuizController.class);
	
	@Autowired
	private QuizAnswerSheetProcessorFactory quizAnswerSheetProcessorFactory;
	
	@Autowired
	private List<QuizQuestionGenerator> quizQuestionGeneratorList;
	
	private Map<String, List<Question>> questionsMap;
	
	@PostConstruct
	public void init() throws Exception {
		
		logger.info("Start to initialize the questions MAP");
		
		questionsMap = new HashMap<String, List<Question>>();
		
		for(QuizQuestionGenerator generator : quizQuestionGeneratorList){
			QuizQuestion quizQuestion = generator.generate();
			if(quizQuestion != null){
				questionsMap.put(quizQuestion.getQuizType(), quizQuestion.getQuestionList());
			}
		}
		
		logger.info("The questions MAP has been initialized");
	}
	
	@GetMapping("quiz/all")
	public String showAllQuiz(){
		
		
		return "quizs";
	}
	
	@GetMapping("quiz/{quizType}")
	public String showQuiz(Principal principal, @PathVariable String quizType, Map<String, Object> model){
		
		String userName = principal.getName();
		
		logger.info("User name is {}", userName);
		
		if(QUIZ_TYPE.getByValue(quizType) == null){
			logger.error("The quiz type {} is not exist", quizType);
			return "404";
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("display Zhi Ye Mao quiz ......");
		}
		List<Question> questionList = questionsMap.get(quizType);
		
		if(questionList == null){
			return "error";
		}
		
		logger.debug("Question size is {}", questionList.size());
		
		QuestionsModel qm = new QuestionsModel();
		qm.setQuestionList(questionList);
		
		model.put("questionModel", qm);
//		model.put("questionMap", questionMap);
		model.put("zymAnswer", ZYM_ANSWER.values());
		
		return "zym";
	}
	
	@PostMapping("quiz/{quizType}")
	public String submitQuiz(@PathVariable String quizType, @ModelAttribute QuestionsModel qm, Map<String, Object> model){
		
		if(QUIZ_TYPE.getByValue(quizType) == null){
			logger.error("The quiz type {} is not exist", quizType);
			return "404";
		}
		
		QuizAnswerSheetProcessor processor = quizAnswerSheetProcessorFactory.getProcessor(quizType);
		
		if(processor == null){
			return "error";
		}
		logger.info("Processor is {}", processor);
		
		processor.process(qm);
		
		List<Question> qmm = qm.getQuestionList();
		
		System.out.println("Quiz id = " + qm.getQuizId());

//		Set<Integer> keys = qmm.keySet();
		
//		for(Integer key : qmm.keySet()){
//			System.out.println("Question = " + qmm.get(key).toString());
//		}
		return "index";
	}

}
