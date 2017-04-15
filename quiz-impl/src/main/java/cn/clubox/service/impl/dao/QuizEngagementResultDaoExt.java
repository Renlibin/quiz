package cn.clubox.service.impl.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.jooq.domain.tables.daos.QuizEngagementResultDao;
import cn.clubox.jooq.domain.tables.pojos.QuizEngagementResult;
import cn.clubox.jooq.domain.tables.records.QuizEngagementResultRecord;

@Repository("quizEngagementResultDao")
public class QuizEngagementResultDaoExt extends QuizEngagementResultDao{

	private static final Logger logger = LoggerFactory.getLogger(QuizEngagementResultDaoExt.class);
	
	@Autowired
	private DSLContext context;
	
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
		}
		
		
		context.batchInsert(records);
		
	}

}
