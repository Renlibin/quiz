package cn.clubox.quiz.web.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.service.api.QuizAnswerSheetProcessor;

@Service
public class QuizAnswerSheetProcessorFactory {

	private static final Logger logger= LoggerFactory.getLogger(QuizAnswerSheetProcessorFactory.class);
			
	@Autowired
	private List<QuizAnswerSheetProcessor> quizAnswerSheetProcessorList;
	
	private Map<String, QuizAnswerSheetProcessor> quizAnswerSheetProcessorMap;
	
	@PostConstruct
	public void init() {
		
		logger.info("Initializing QuizAnswerSheetProcessors");
		
		quizAnswerSheetProcessorMap = new HashMap<String, QuizAnswerSheetProcessor>();
		
		for(QuizAnswerSheetProcessor processor : quizAnswerSheetProcessorList){
			quizAnswerSheetProcessorMap.put(processor.getQuizName(), processor);
		}
	}
	
	public QuizAnswerSheetProcessor getProcessor(String quiz){
		
		return quizAnswerSheetProcessorMap.get(quiz);
	}

}
