package cn.clubox.quiz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;

@Service
public class IseqQuizAnswerSheetProcessor extends AbstractQuizAnswerSheetProcessor{
	
	private static final Logger logger = LoggerFactory.getLogger(IseqQuizAnswerSheetProcessor.class);
	
	@Override
	public String getQuizName() {
		if(logger.isDebugEnabled()){
			logger.debug("Quiz name is {}", QUIZ_TYPE.ISEQ.value);
		}
		return QUIZ_TYPE.ISEQ.value;
	}
	
	@Override
	public String getResultOption(Question question){
		return "all";
	}

}
