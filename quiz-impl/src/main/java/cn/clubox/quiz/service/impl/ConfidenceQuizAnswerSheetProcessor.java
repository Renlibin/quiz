package cn.clubox.quiz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;

@Service
public class ConfidenceQuizAnswerSheetProcessor extends AbstractQuizAnswerSheetProcessor {
	
	private Logger logger = LoggerFactory.getLogger(ConfidenceQuizAnswerSheetProcessor.class);
	
	@Override
	public String getQuizName() {
		if(logger.isDebugEnabled()){
			logger.debug("Quiz name is {}", QUIZ_TYPE.CONFIDENCE.value);
		}
		return QUIZ_TYPE.CONFIDENCE.value;
	}
	
}
