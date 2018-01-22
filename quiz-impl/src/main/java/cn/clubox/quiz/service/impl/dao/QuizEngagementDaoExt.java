package cn.clubox.quiz.service.impl.dao;

import static cn.clubox.quiz.jooq.domain.tables.QuizEngagement.QUIZ_ENGAGEMENT;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.quiz.jooq.domain.tables.daos.QuizEngagementDao;
import cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagement;
import cn.clubox.quiz.service.api.util.Status;

@Repository("quizEngagementDao")
public class QuizEngagementDaoExt extends QuizEngagementDao{
	
	private static final Logger logger = LoggerFactory.getLogger(QuizEngagementDaoExt.class);
	
	@Autowired
	private DSLContext context;
	
	public int insertWithReturning(QuizEngagement quizEngagement){
		
		if(logger.isDebugEnabled()){
			logger.debug("Insert quiz engagement");
		}
		
		Record record = context.insertInto(QUIZ_ENGAGEMENT, QUIZ_ENGAGEMENT.QUIZ_ID, QUIZ_ENGAGEMENT.USER_ID,
				QUIZ_ENGAGEMENT.DURATION, QUIZ_ENGAGEMENT.STATUS,QUIZ_ENGAGEMENT.STORED,QUIZ_ENGAGEMENT.UPDATED)
			   .values(quizEngagement.getQuizId(), quizEngagement.getUserId(), quizEngagement.getDuration(), 
					   quizEngagement.getStatus() ,new Timestamp(new Date().getTime()),new Timestamp(new Date().getTime()))
			   .returning(QUIZ_ENGAGEMENT.ID).fetchOne();
		
		return record.getValue(QUIZ_ENGAGEMENT.ID);
		
	}
	
	public List<Integer> fetchEngagedQuizId (int userId){
		
		List<Integer> quizIdList = context.selectDistinct(QUIZ_ENGAGEMENT.QUIZ_ID).from(QUIZ_ENGAGEMENT)
			.where(QUIZ_ENGAGEMENT.USER_ID.eq(userId).and(QUIZ_ENGAGEMENT.STATUS.eq(Status.COMPLETE.getValue()))).fetchInto(Integer.class);
		
		return quizIdList;
	}
	
	public void updateQuizEngagementStatus(int engagementId, Status status){
		
		if(logger.isDebugEnabled()){
			logger.debug("Quiz engagement's status is going to be updated to {}", status.getValue());
		}
		
		context.update(QUIZ_ENGAGEMENT).set(QUIZ_ENGAGEMENT.STATUS,status.getValue())
		.set(QUIZ_ENGAGEMENT.UPDATED,new Timestamp(new Date().getTime())).where(QUIZ_ENGAGEMENT.ID.eq(engagementId)).execute();
	}
	
	public List<Integer> fetchAllDistinctLatestEngagedQuizId(int userId){
		
//		List<Integer> engagementIdList = context.select(quizEngagementTable.ID).from(quizEngagementTable).where (
//				quizEngagementTable.ID.eq(
//							context.select(DSL.maxDistinct(quizEngagementTable.ID))
//								.from(quizEngagementTable).where(quizEngagementTable.USER_ID.eq(userId)).groupBy(quizEngagementTable.QUIZ_ID)
//						)
//				).fetchInto(Integer.class);
		
		
		List<Integer> quizIdList = context.selectDistinct(QUIZ_ENGAGEMENT.QUIZ_ID).from(QUIZ_ENGAGEMENT)
				.where(QUIZ_ENGAGEMENT.USER_ID.eq(userId).and(QUIZ_ENGAGEMENT.STATUS.eq(Status.COMPLETE.getValue()))).fetchInto(Integer.class);
		
		return quizIdList;
	}
	
	public int countTotalParticipant(int quizId){
		
		return context.selectDistinct(QUIZ_ENGAGEMENT.USER_ID).from(QUIZ_ENGAGEMENT)
			.where(QUIZ_ENGAGEMENT.QUIZ_ID.eq(quizId).and(QUIZ_ENGAGEMENT.STATUS.eq(Status.COMPLETE.getValue()))).fetch().size();
	}
	
}
