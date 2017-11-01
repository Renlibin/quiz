package cn.clubox.quiz.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
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
import cn.clubox.quiz.service.api.util.QuizEngagementStatus;
import cn.clubox.quiz.service.impl.dao.QuizEngagementDaoExt;
import cn.clubox.quiz.service.impl.dao.QuizEngagementResultDaoExt;

public abstract class AbstractQuizAnswerSheetProcessor implements QuizAnswerSheetProcessor<Short> {

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
		
//		short totalScore = this.countTotalScore(quizAnswerSheet.getQuestionList());
		
		int quizEngagementId = quizAnswerSheet.getEngagementId();
		if(logger.isDebugEnabled()){
			logger.debug("Engagement id is {}", quizEngagementId);
		}
		if( quizEngagementId == 0 || quizAnswerSheet.isLastPage()){
			quizEngagementId = this.persistQuizEngagement(quizAnswerSheet);
		}
		
		this.persistQuizEngagementResult(quizEngagementId, quizAnswerSheet.getQuestionList());
		return quizEngagementId;
	}
	
//	@Override
//	public Short countTotalScore(List<Question> questions){
//		
//		short totalScore = 0;
//		
//		if(questions == null || questions.isEmpty()){
//			return totalScore;
//		}
//		for(Question question : questions){
//			
//			if(logger.isDebugEnabled()){
//				logger.debug("Selected option key of quiz {} is {}", this.getQuizName(), question.getSelectedOptionKey());
//			}
//			
//			totalScore = (short)(totalScore + question.getSelectedOptionKey());
//			
//		}
//		return totalScore;
//	}
	
	
	@Override
	public int persistQuizEngagement(QuizAnswerSheet quizAnswerSheet){
		
		if(logger.isDebugEnabled()){
			logger.debug("Persisting Quiz Engagement");
		}
		
		int quizEngagementId;
		
		if(quizAnswerSheet.isLastPage()){
			quizEngagementId = quizAnswerSheet.getEngagementId();
			quizEngagementDao.updateQuizEngagementStatus(quizEngagementId, QuizEngagementStatus.COMPLETE);
		}else{
			QuizEngagement quizEngagement = new QuizEngagement();
			quizEngagement.setQuizId(quizAnswerSheet.getQuizId());
			quizEngagement.setUserId(quizAnswerSheet.getUserId());
			quizEngagement.setDuration(quizAnswerSheet.getDuration());
			quizEngagement.setStatus(QuizEngagementStatus.UNCOMPLETED.getValue());
			quizEngagement.setStored(new Timestamp(new Date().getTime()));
			quizEngagementId = quizEngagementDao.insertWithReturning(quizEngagement);
		}
		return quizEngagementId;
		
	}
	
//	@Override
//	public void persistQuizEngagementResult(int quizEngagementId, Short score){
//		
//		if(logger.isDebugEnabled()){
//			logger.debug("Persisting Quiz Engagement Result");
//		}
//		
//		QuizEngagementResult qer = new QuizEngagementResult();
//		qer.setQuizEngagementId(quizEngagementId);
//		qer.setResultOption("all");
//		qer.setScore(score); /** Score of QuizEngagementResult should be changed to short */
//		qer.setStored(new Timestamp(new Date().getTime()));
//		
//		quizEngagementResultDao.insert(qer);
//	}
	
	@Override
	public void  persistQuizEngagementResult(int quizEngagementId, List<? extends Question> questions){
		
		List<QuizEngagementResult> quizEngagementResults = new ArrayList<>();
		for(Question question : questions){
			QuizEngagementResult qer = new QuizEngagementResult();
			if(question.getEngagementResultId() != 0){
				qer.setId(question.getEngagementResultId());
			}
			qer.setQuizEngagementId(quizEngagementId);
			qer.setQuestionId(question.getSequenceNumber());
			qer.setResultOption(this.getResultOption(question));
			qer.setScore(question.getSelectedOptionKey());
			qer.setStored(new Timestamp(new Date().getTime()));
			quizEngagementResults.add(qer);
		}
		
		quizEngagementResultDao.insertOrUpdateMultipleRecords(quizEngagementResults);
	}
	
	
	public abstract String getResultOption(Question question);
}
