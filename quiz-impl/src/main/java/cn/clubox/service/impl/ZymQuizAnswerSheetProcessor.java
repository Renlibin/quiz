package cn.clubox.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.service.api.QuizAnswerSheetProcessor;
import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.QuestionsModel;
import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;
import cn.clubox.service.impl.dao.QuizEngagementDaoExt;
import cn.clubox.service.impl.dao.QuizEngagementResultDaoExt;

@Service
public class ZymQuizAnswerSheetProcessor implements QuizAnswerSheetProcessor {

	private static final Logger logger = LoggerFactory.getLogger(ZymQuizAnswerSheetProcessor.class);
	
	@Autowired
	private QuizEngagementDaoExt quizEngagementDao;
	
	@Autowired
	private QuizEngagementResultDaoExt quizEngagementResultDao;
	
	@Override
	public void process(QuestionsModel questionModel) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Start to process ZYM quiz answer sheet");
		}
		
		int score = 0;
		
		for(Question question : questionModel.getQuestionList()){
			
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

	@Override
	public String getQuizName() {
		
		return QUIZ_TYPE.zym.toString();
	}

}

