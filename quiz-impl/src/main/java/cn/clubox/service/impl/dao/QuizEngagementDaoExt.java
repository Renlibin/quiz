package cn.clubox.service.impl.dao;

import java.sql.Timestamp;
import java.util.Date;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.jooq.domain.tables.daos.QuizEngagementDao;
import cn.clubox.jooq.domain.tables.pojos.QuizEngagement;

@Repository("quizEngagementDao")
public class QuizEngagementDaoExt extends QuizEngagementDao{
	
	private static final Logger logger = LoggerFactory.getLogger(QuizEngagementDaoExt.class);
	
	@Autowired
	private DSLContext context;
	
	public int insertWithReturning(QuizEngagement quizEngagement){
		
		if(logger.isDebugEnabled()){
			logger.debug("Insert quiz engagement");
		}
		
		cn.clubox.jooq.domain.tables.QuizEngagement quizEngagementTable =  cn.clubox.jooq.domain.tables.QuizEngagement.QUIZ_ENGAGEMENT;
		
		Record record = context.insertInto(quizEngagementTable, quizEngagementTable.QUIZ_ID, quizEngagementTable.USER_ID,
				quizEngagementTable.DURATION, quizEngagementTable.STORED)
			   .values(quizEngagement.getQuizId(), quizEngagement.getUserId(), quizEngagement.getDuration(), new Timestamp(new Date().getTime()))
			   .returning(quizEngagementTable.ID).fetchOne();
		
		return record.getValue(quizEngagementTable.ID);
		
	}
}
