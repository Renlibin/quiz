package cn.clubox.web.utils;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.service.api.QuizAnswerSheetProcessor;

@Service
public class QuizAnswerSheetProcessorFactory implements InitializingBean {

	private static final Logger logger= LoggerFactory.getLogger(QuizAnswerSheetProcessorFactory.class);
			
	@Autowired
	private List<QuizAnswerSheetProcessor> quizAnswerSheetProcessorList;
	
	private Map<String, QuizAnswerSheetProcessor> quizAnswerSheetProcessorMap;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		for(QuizAnswerSheetProcessor processor : quizAnswerSheetProcessorList){
			quizAnswerSheetProcessorMap.put(processor.getQuizName(), processor);
		}
	}
	
	public QuizAnswerSheetProcessor getProcessor(String quiz){
		
		return quizAnswerSheetProcessorMap.get(quiz);
	}

}
