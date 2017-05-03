package cn.clubox.quiz.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagement;
import cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult;
import cn.clubox.quiz.service.api.QuizAnswerSheetProcessor;
import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;
import cn.clubox.quiz.service.api.model.QuizAnswerSheet;
import cn.clubox.quiz.service.impl.dao.QuizEngagementDaoExt;
import cn.clubox.quiz.service.impl.dao.QuizEngagementResultDaoExt;

@Service
public class IseqQuizAnswerSheetProcessor implements QuizAnswerSheetProcessor{
	
	private static final Logger logger = LoggerFactory.getLogger(IseqQuizAnswerSheetProcessor.class);
	
	@Autowired
	private QuizEngagementDaoExt quizEngagementDao;
	
	@Autowired
	private QuizEngagementResultDaoExt quizEngagementResultDao;

	@Override
	public String getQuizName() {
		return QUIZ_TYPE.ISEQ.value;
	}

	@Override
	public int process(QuizAnswerSheet quizAnswerSheet) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Start to process ISEQ quiz answer sheet");
		}
		
		int totalScore = 0;
		
		for(Question question : quizAnswerSheet.getQuestionList()){
			
			totalScore = totalScore + question.getSelectedOptionKey();
			
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("The final score is {}", totalScore);
		}
		
		int quizEngagementId = this.persistQuizEngagement(quizAnswerSheet);
		this.persistQuizEngagementResult(quizEngagementId, totalScore);
		return quizEngagementId;
	}
	
	/****** private methods ******/
	
	private int persistQuizEngagement(QuizAnswerSheet quizAnswerSheet){
		
		if(logger.isDebugEnabled()){
			logger.debug("Persisting Quiz Engagement");
		}
		
		QuizEngagement quizEngagement = new QuizEngagement();
		quizEngagement.setQuizId(quizAnswerSheet.getQuizId());
		quizEngagement.setUserId(quizAnswerSheet.getUserId());
		quizEngagement.setDuration(quizAnswerSheet.getDuration());
		quizEngagement.setStored(new Timestamp(new Date().getTime()));
		
		int quizEngagementId = quizEngagementDao.insertWithReturning(quizEngagement);
		
		return quizEngagementId;
		
	}
	
	private void persistQuizEngagementResult(int quizEngagementId, int score){
		
		if(logger.isDebugEnabled()){
			logger.debug("Persisting Quiz Engagement Result");
		}
		
		QuizEngagementResult qer = new QuizEngagementResult();
		qer.setQuizEngagementId(quizEngagementId);
		qer.setResultOption("all");
		qer.setScore(score); /** Score of QuizEngagementResult should be changed to short */
		qer.setStored(new Timestamp(new Date().getTime()));
		
		quizEngagementResultDao.insert(qer);
	}

}
