package cn.clubox.service.impl.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.jooq.domain.tables.Quiz;
import cn.clubox.jooq.domain.tables.QuizQuestion;
import cn.clubox.jooq.domain.tables.daos.QuizQuestionDao;
import cn.clubox.jooq.domain.tables.records.QuizQuestionRecord;

@Repository
public class QuizQuestionDaoExt extends QuizQuestionDao {
	
	private static final Logger logger = LoggerFactory.getLogger(QuizQuestionDaoExt.class);

	@Autowired
	private DSLContext context;
	
	@PostConstruct
	public void config(){
		logger.info("Initializing JOOQ configuration");
		super.setConfiguration(context.configuration());
	}
	
	public List<QuizQuestionRecord> fetchQuizQuestionByQuizType(String quizType){
		
		if(logger.isDebugEnabled()){
			logger.debug("Fetching questions of QUIZ {}", quizType);
		}
		
		Quiz quizTable = Quiz.QUIZ_;
		QuizQuestion quizQuestionTable = QuizQuestion.QUIZ_QUESTION;
		
		List<QuizQuestionRecord> results = context.selectFrom(quizTable.join(quizQuestionTable).onKey())
		.where(quizTable.QUIZ_TYPE.eq(quizType)).orderBy(quizQuestionTable.SEQUENCE_NUMBER.asc())
		.fetchInto(QuizQuestionRecord.class);
		
		if(logger.isDebugEnabled()){
			logger.debug("Fetched {} QUIZ questions", results.size());
		}
		
		return results;
	}
	
}
