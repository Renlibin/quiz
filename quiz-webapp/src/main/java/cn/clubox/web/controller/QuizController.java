package cn.clubox.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import cn.clubox.quiz.service.api.QuizAnswerSheetProcessor;
import cn.clubox.quiz.service.api.QuizQuestionGenerator;
import cn.clubox.quiz.service.api.QuizQuestionGenerator.QuizQuestion;
import cn.clubox.quiz.service.api.model.Option;
import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.QuestionsModel;
import cn.clubox.web.utils.QuizAnswerSheetProcessorFactory;
import cn.clubox.web.utils.ZYM_ANSWER;

@Controller
public class QuizController implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(QuizController.class);
	
	@Autowired
	private QuizAnswerSheetProcessorFactory quizAnswerSheetProcessorFactory;
	
	@Autowired
	private List<QuizQuestionGenerator> quizQuestionGeneratorList;
	
	private Map<String, List<Question>> questionsMap;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		logger.info("Start to initialize the questions MAP");
		
		for(QuizQuestionGenerator generator : quizQuestionGeneratorList){
			QuizQuestion quizQuestion = generator.generate();
			questionsMap.put(quizQuestion.getQuiz(), quizQuestion.getQuestionList());
		}
		
		logger.info("The questions MAP has been initialized");
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
//	@GetMapping("/zym")
	@GetMapping("quiz/{quizName}")
	public String displayZYM(@PathVariable String quizName, Map<String, Object> model){
		
		if(logger.isDebugEnabled()){
			logger.debug("display Zhi Ye Mao quiz ......");
		}
		
		Option o1 = new Option();
		o1.setOptionOrderNumber(1);
		o1.setOptionText("有时");
		
		Option o2 = new Option();
		o2.setOptionOrderNumber(2);
		o2.setOptionText("总是");
		
//		Map<Integer,Option> optionMap = new TreeMap<Integer,Option>();
//		optionMap.put(o1.getOptionKey(), o1);
//		optionMap.put(o2.getOptionKey(), o2);
		
		List<Option> optionList = new ArrayList<Option>();
		optionList.add (o1);
		optionList.add(o2);
		
		Question one = new Question();
		one.setId(1);
		one.setTitle("Question one");
		one.setOptionList(optionList);
		
		Question two = new Question();
		two.setId(2);
		two.setTitle("Question two");
		two.setOptionList(optionList);
		
		List<Question> questionMap = new ArrayList<Question>();
		questionMap.add(one);
		questionMap.add(two);
		
		QuestionsModel qm = new QuestionsModel();
		qm.setQuestionList(questionMap);
		
		model.put("questionModel", qm);
//		model.put("questionMap", questionMap);
		model.put("zymAnswer", ZYM_ANSWER.values());
		
		return "zym";
	}
	
//	@GetMapping("zym")
	@PostMapping("quiz/{quizName}")
	public String submitZYM(@ModelAttribute QuestionsModel qm, Map<String, Object> model){
		
		QuizAnswerSheetProcessor processor = quizAnswerSheetProcessorFactory.getProcessor("");
		processor.process(qm.getQuestionList());
		
		List<Question> qmm = qm.getQuestionList();
		
		System.out.println("Quiz id = " + qm.getQuizId());

//		Set<Integer> keys = qmm.keySet();
		System.out.println("qmm = " + qmm);
		
//		for(Integer key : qmm.keySet()){
//			System.out.println("Question = " + qmm.get(key).toString());
//		}
		return "index";
	}

}
