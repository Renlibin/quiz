package cn.clubox.quiz.service.impl.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
	public void insertMultipleRecords(List<QuizEngagementResult> quizEngagementResultList){
		
		if(logger.isDebugEnabled()){
			logger.debug("Insert quiz engagement result");
		}
		
		List<QuizEngagementResultRecord> records = new ArrayList<QuizEngagementResultRecord>();
		
		for(QuizEngagementResult quizEngagementResult : quizEngagementResultList){
			QuizEngagementResultRecord record = new QuizEngagementResultRecord();
			
			record.setQuizEngagementId(quizEngagementResult.getQuizEngagementId());
			record.setResultOption(quizEngagementResult.getResultOption());
			record.setScore(quizEngagementResult.getScore());
			record.setStored(new Timestamp(new Date().getTime()));
			records.add(record);
			
			logger.info("Records size is {}",records.size());
		}
		
		int[] results =context.batchInsert(records).execute();
		
		if(logger.isInfoEnabled()){
			logger.debug("Results of persisting of QuizEngagement are {}", results.toString());
		}

	}
	
	public List<QuizEngagementResult> fetchQuizEngagementByUserIdAndQuizType(int userId, String quizType){
		
		cn.clubox.quiz.jooq.domain.tables.QuizEngagementResult quizEngagementResultTable = cn.clubox.quiz.jooq.domain.tables.QuizEngagementResult.QUIZ_ENGAGEMENT_RESULT;
		
		List<QuizEngagementResult> quizEngagementResultList = 
				 context.select(quizEngagementResultTable.ID, 
						 quizEngagementResultTable.RESULT_OPTION,quizEngagementResultTable.SCORE).from(quizEngagementResultTable)
				 .where(quizEngagementResultTable.QUIZ_ENGAGEMENT_ID.in(
					 context.select(DSL.maxDistinct(QuizEngagement.QUIZ_ENGAGEMENT.ID))
					.from(Quiz.QUIZ_).innerJoin(QuizEngagement.QUIZ_ENGAGEMENT).on(Quiz.QUIZ_.ID.eq(QuizEngagement.QUIZ_ENGAGEMENT.QUIZ_ID))
					.innerJoin(quizEngagementResultTable).on(QuizEngagement.QUIZ_ENGAGEMENT.ID.eq(quizEngagementResultTable.QUIZ_ENGAGEMENT_ID))
					.where(Quiz.QUIZ_.QUIZ_TYPE.eq(quizType)).and(QuizEngagement.QUIZ_ENGAGEMENT.USER_ID.eq(userId)).groupBy(QuizEngagement.QUIZ_ENGAGEMENT.QUIZ_ID)
					.fetchInto(Integer.class)
				 )).fetchInto(QuizEngagementResult.class);
		
		return quizEngagementResultList;
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
