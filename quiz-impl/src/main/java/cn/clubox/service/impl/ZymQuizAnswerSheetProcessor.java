package cn.clubox.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.service.api.QuizAnswerSheetProcessor;
import cn.clubox.quiz.service.api.model.Question;

@Service
public class ZymQuizAnswerSheetProcessor implements QuizAnswerSheetProcessor {

	private static final Logger logger = LoggerFactory.getLogger(ZymQuizAnswerSheetProcessor.class);
	
	@Override
	public void process(List<Question> questions) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Start to process ZYM quiz answer sheet");
		}
		
		int score = 0;
		
		for(Question question : questions){
			
			//Question id will be used to retrieve score of the question according to the selected option
			int qid = question.getId();
			int spk = question.getSelectedOptionKey();
			score = score + spk;
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("The final score is {}", score);
		}
		
		//The result of answer sheet should be stored in DB
	}

}

