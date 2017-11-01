package cn.clubox.quiz.service.impl.dao;

import static cn.clubox.quiz.jooq.domain.tables.QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.quiz.jooq.domain.tables.Quiz;
import cn.clubox.quiz.jooq.domain.tables.QuizEngagement;
import cn.clubox.quiz.jooq.domain.tables.daos.QuizEngagementResultDao;
import cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult;
import cn.clubox.quiz.jooq.domain.tables.records.QuizEngagementResultRecord;

@Repository("quizEngagementResultDao")
public class QuizEngagementResultDaoExt extends QuizEngagementResultDao{

	private static final Logger logger = LoggerFactory.getLogger(QuizEngagementResultDaoExt.class);
	
	@Autowired
	private DSLContext context;
	
	@PostConstruct
	public void config(){
		super.setConfiguration(context.configuration());
	}
	
	public void insertOrUpdateMultipleRecords(List<QuizEngagementResult> quizEngagementResultList){
		
		if(logger.isDebugEnabled()){
			logger.debug("Insert quiz engagement result");
		}
		
		int[] results;
		List<QuizEngagementResultRecord> records = new ArrayList<>();
		Set<Integer> ids = quizEngagementResultList.stream().filter(q -> q.getId() != null).map(i -> i.getId()).collect(Collectors.toSet());
		
		if(ids != null && ids.isEmpty() == false){
			if(logger.isDebugEnabled()){
				logger.debug("Quiz engagement results are going to be updated");
			}
			for(QuizEngagementResult quizEngagementResult : quizEngagementResultList){
				QuizEngagementResultRecord record = new QuizEngagementResultRecord();
				record.setId(quizEngagementResult.getId());
				record.setQuizEngagementId(quizEngagementResult.getQuizEngagementId());
				record.setQuestionId(quizEngagementResult.getQuestionId());
				record.setResultOption(quizEngagementResult.getResultOption());
				record.setScore(quizEngagementResult.getScore());
				record.setStored(new Timestamp(new Date().getTime()));
				records.add(record);
			}
			results = context.batchUpdate(records).execute();
		}else{
			for(QuizEngagementResult quizEngagementResult : quizEngagementResultList){
				QuizEngagementResultRecord record = new QuizEngagementResultRecord();
				record.setQuizEngagementId(quizEngagementResult.getQuizEngagementId());
				record.setQuestionId(quizEngagementResult.getQuestionId());
				record.setResultOption(quizEngagementResult.getResultOption());
				record.setScore(quizEngagementResult.getScore());
				record.setStored(new Timestamp(new Date().getTime()));
				records.add(record);
				
				logger.info("{} records are going to be inserted",records.size());
			}
			
			results =context.batchInsert(records).execute();
		}
		if(logger.isInfoEnabled()){
			logger.debug("Results of persisting of QuizEngagement are {}", results.toString());
		}
	}
	
	public List<QuizEngagementResult> fetchQuizEngagementByUserIdAndQuizType(int userId, String quizType){
		
		List<QuizEngagementResult> quizEngagementResultList = 
				 context.select(QUIZ_ENGAGEMENT_RESULT.ID, 
						 QUIZ_ENGAGEMENT_RESULT.RESULT_OPTION,QUIZ_ENGAGEMENT_RESULT.SCORE).from(QUIZ_ENGAGEMENT_RESULT)
				 .where(QUIZ_ENGAGEMENT_RESULT.QUIZ_ENGAGEMENT_ID.in(
					 context.select(DSL.maxDistinct(QuizEngagement.QUIZ_ENGAGEMENT.ID))
					.from(Quiz.QUIZ_).innerJoin(QuizEngagement.QUIZ_ENGAGEMENT).on(Quiz.QUIZ_.ID.eq(QuizEngagement.QUIZ_ENGAGEMENT.QUIZ_ID))
					.innerJoin(QUIZ_ENGAGEMENT_RESULT).on(QuizEngagement.QUIZ_ENGAGEMENT.ID.eq(QUIZ_ENGAGEMENT_RESULT.QUIZ_ENGAGEMENT_ID))
					.where(Quiz.QUIZ_.QUIZ_TYPE.eq(quizType)).and(QuizEngagement.QUIZ_ENGAGEMENT.USER_ID.eq(userId))
					.and(QuizEngagement.QUIZ_ENGAGEMENT.STATUS.eq("C")).groupBy(QuizEngagement.QUIZ_ENGAGEMENT.QUIZ_ID)
					.fetchInto(Integer.class)
				 )).fetchInto(QuizEngagementResult.class);
		
		return quizEngagementResultList;
	}
	
	public List<QuizEngagementResult> fetchQuizEngagementResultByIdAndRang(int engagementId, int start , int end){
		
		if(logger.isDebugEnabled()){
		    logger.debug("Engagement result are going to be retrieved according to engagement id {} and question rang {} to {}", engagementId, start, end);
		}
		return context.select(QUIZ_ENGAGEMENT_RESULT.ID,QUIZ_ENGAGEMENT_RESULT.QUESTION_ID,QUIZ_ENGAGEMENT_RESULT.SCORE).from(QUIZ_ENGAGEMENT_RESULT)
			.where(QUIZ_ENGAGEMENT_RESULT.QUIZ_ENGAGEMENT_ID.eq(engagementId))
			.and(QUIZ_ENGAGEMENT_RESULT.QUESTION_ID.ge((short)start).and(QUIZ_ENGAGEMENT_RESULT.QUESTION_ID.le((short)end))).fetchInto(QuizEngagementResult.class);
		
	}
	
//	public List<QuizEngagementResult> fetchByUserIdAndQuizEngagementId(int quizEngagementId){
//		
//		cn.clubox.quiz.jooq.domain.tables.QuizEngagementResult quizEngagementResultTable = cn.clubox.quiz.jooq.domain.tables.QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT;
//		
//		List<QuizEngagementResult> quizEngagementResultList = context.select(quizEngagementResultTable.RESULT_OPTION,quizEngagementResultTable.SCORE).from(quizEngagementResultTable)
//			.where(quizEngagementResultTable.QUIZ_ENGAGEMENT_ID.eq(quizEngagementId)).fetchInto(QuizEngagementResult.class);
//		
//		return quizEngagementResultList;
//	}
	

}
