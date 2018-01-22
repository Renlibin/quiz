package cn.clubox.quiz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.Quiz.QuizType;

@Service
public class ConfidenceQuizAnswerSheetProcessor extends AbstractQuizAnswerSheetProcessor {
	
	private Logger logger = LoggerFactory.getLogger(ConfidenceQuizAnswerSheetProcessor.class);
	
	@Override
	public String getQuizName() {
		if(logger.isDebugEnabled()){
			logger.debug("Quiz name is {}", QuizType.CONFIDENCE.value);
		}
		return QuizType.CONFIDENCE.value;
	}

	@Override
	public String getResultOption(Question question) {
		return "all";
	}
	
}
