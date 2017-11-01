package cn.clubox.quiz.service.impl.dao;

import static cn.clubox.quiz.jooq.domain.tables.Quiz.QUIZ_;
import static cn.clubox.quiz.jooq.domain.tables.QuizQuestion.QUIZ_QUESTION;
import static cn.clubox.quiz.jooq.domain.tables.QuizQuestionOption.QUIZ_QUESTION_OPTION;

import java.util.List;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.quiz.jooq.domain.tables.daos.QuizQuestionOptionDao;
import cn.clubox.quiz.jooq.domain.tables.records.QuizQuestionOptionRecord;;

@Repository
public class QuizQuestionOptionDaoExt extends QuizQuestionOptionDao {

	private static Logger logger = LoggerFactory.getLogger(QuizQuestionOptionDaoExt.class);
	
	@Autowired
	private DSLContext context;
	
	public List<QuizQuestionOptionRecord> fetchQuizId(Integer quizId){
		
		if(logger.isDebugEnabled()){
			logger.debug("The question options of quiz {} are going to be retrieved", quizId);
		}
		
		return context.selectFrom(QUIZ_.innerJoin(QUIZ_QUESTION).on(QUIZ_.ID.eq(QUIZ_QUESTION.QUIZ_ID))
				.innerJoin(QUIZ_QUESTION_OPTION).on(QUIZ_QUESTION.ID.eq(QUIZ_QUESTION_OPTION.QUIZ_QUESTION_ID)))
		.where(QUIZ_.ID.eq(quizId)).fetchInto(QuizQuestionOptionRecord.class);
	}
	
}
