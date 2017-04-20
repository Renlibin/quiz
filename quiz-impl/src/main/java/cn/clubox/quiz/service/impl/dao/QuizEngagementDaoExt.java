package cn.clubox.quiz.service.impl.dao;

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

@Repository("quizEngagementDao")
public class QuizEngagementDaoExt extends QuizEngagementDao{
	
	private static final Logger logger = LoggerFactory.getLogger(QuizEngagementDaoExt.class);
	
	@Autowired
	private DSLContext context;
	
	private final cn.clubox.quiz.jooq.domain.tables.QuizEngagement quizEngagementTable =  cn.clubox.quiz.jooq.domain.tables.QuizEngagement.QUIZ_ENGAGEMENT;
	
	public int insertWithReturning(QuizEngagement quizEngagement){
		
		if(logger.isDebugEnabled()){
			logger.debug("Insert quiz engagement");
		}
		
		Record record = context.insertInto(quizEngagementTable, quizEngagementTable.QUIZ_ID, quizEngagementTable.USER_ID,
				quizEngagementTable.DURATION, quizEngagementTable.STORED)
			   .values(quizEngagement.getQuizId(), quizEngagement.getUserId(), quizEngagement.getDuration(), new Timestamp(new Date().getTime()))
			   .returning(quizEngagementTable.ID).fetchOne();
		
		return record.getValue(quizEngagementTable.ID);
		
	}
	
	public List<Integer> fetchEngagedQuizId (int userId){
		
		List<Integer> quizIdList = context.selectDistinct(quizEngagementTable.QUIZ_ID).from(quizEngagementTable)
			.where(quizEngagementTable.USER_ID.eq(userId)).fetchInto(Integer.class);
		
		return quizIdList;
	}
}
