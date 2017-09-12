package cn.clubox.quiz.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagement;
import cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult;
import cn.clubox.quiz.service.api.QuizAnswerSheetProcessor;
import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.QuizAnswerSheet;
import cn.clubox.quiz.service.impl.dao.QuizEngagementDaoExt;
import cn.clubox.quiz.service.impl.dao.QuizEngagementResultDaoExt;

public abstract class AbstractQuizAnswerSheetProcessor implements QuizAnswerSheetProcessor<Integer> {

	private static final Logger logger = LoggerFactory.getLogger(AbstractQuizAnswerSheetProcessor.class);
	
	@Autowired
	private QuizEngagementDaoExt quizEngagementDao;
	
	@Autowired
	private QuizEngagementResultDaoExt quizEngagementResultDao;
	
	@Override
	public abstract String getQuizName();
	
	@Override
	public int process(QuizAnswerSheet quizAnswerSheet){
		if(logger.isDebugEnabled()){
			logger.debug("Start to process quiz {} answer sheet",quizAnswerSheet.getQuizId());
		}
		
		int totalScore = this.countTotalScore(quizAnswerSheet.getQuestionList());
		int quizEngagementId = this.persistQuizEngagement(quizAnswerSheet);
		this.persistQuizEngagementResult(quizEngagementId, totalScore);
		return quizEngagementId;
	}
	
	@Override
	public Integer countTotalScore(List<Question> questions){
		
		Integer totalScore = 0;
		
		if(questions == null || questions.isEmpty()){
			return totalScore;
		}
		for(Question question : questions){
			
			totalScore = totalScore + question.getSelectedOptionKey();
			
		}
		return totalScore;
	}
	
	
	@Override
	public int persistQuizEngagement(QuizAnswerSheet quizAnswerSheet){
		
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
	
	@Override
	public void persistQuizEngagementResult(int quizEngagementId, Integer score){
		
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
