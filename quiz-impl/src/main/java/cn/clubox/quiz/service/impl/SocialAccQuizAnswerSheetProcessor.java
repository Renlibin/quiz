package cn.clubox.quiz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;

@Service
public class SocialAccQuizAnswerSheetProcessor extends AbstractQuizAnswerSheetProcessor {

	private static final Logger logger = LoggerFactory.getLogger(SocialAccQuizAnswerSheetProcessor.class);
	
	@Override
	public String getQuizName() {
		if(logger.isDebugEnabled()){
			logger.debug("Quiz name is {}", QUIZ_TYPE.SOCIALACC.value);
		}
		return QUIZ_TYPE.SOCIALACC.value;
	}

}
