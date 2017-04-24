package cn.clubox.quiz.service.impl.dao;

import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.quiz.jooq.domain.tables.Quiz;
import cn.clubox.quiz.jooq.domain.tables.QuizEngagement;
import cn.clubox.quiz.jooq.domain.tables.UserPayment;
import cn.clubox.quiz.jooq.domain.tables.daos.UserPaymentDao;

@Repository
public class UserPaymentDaoExt extends UserPaymentDao {
	
	private static final Logger logger = LoggerFactory.getLogger(UserPaymentDaoExt.class);

	@Autowired
	private DSLContext context;
	
	@PostConstruct
	public void config(){
		super.setConfiguration(context.configuration());
	}
	
	public List<Integer> fetchPayedQuizId(int userId){
		
		List<Integer> payedQuizIdList = context.selectDistinct(UserPayment.USER_PAYMENT.QUIZ_ID).from(UserPayment.USER_PAYMENT)
			.where(UserPayment.USER_PAYMENT.USER_ID.eq(userId)).fetchInto(Integer.class);
		
		if(logger.isDebugEnabled()){
			logger.debug("Payed quiz ids are {}", new HashSet<Integer>(payedQuizIdList).toString());
		}
		
		return payedQuizIdList;
	}
	
	public List<Integer> fetchUndoneQuizId(int userId){
		
		List<Integer> undoneQuizIdList = context.select(UserPayment.USER_PAYMENT.QUIZ_ID).from(UserPayment.USER_PAYMENT)
			.leftJoin(QuizEngagement.QUIZ_ENGAGEMENT).on(UserPayment.USER_PAYMENT.QUIZ_ID.eq(QuizEngagement.QUIZ_ENGAGEMENT.QUIZ_ID))
			.where(UserPayment.USER_PAYMENT.USER_ID.eq(userId)
					.and(QuizEngagement.QUIZ_ENGAGEMENT.QUIZ_ID.isNull())).fetchInto(Integer.class);
		
		if(logger.isDebugEnabled()){
			logger.debug("Undone quiz ids are {}", new HashSet<Integer>(undoneQuizIdList).toString());
		}
		
		return undoneQuizIdList;
	}
	
	public List<Integer> fetchUserPaymentByUserIdAndQuizType(int userId, String quizType){
		
		List<Integer> paymentIdList = context.select(UserPayment.USER_PAYMENT.ID).from(UserPayment.USER_PAYMENT).innerJoin(Quiz.QUIZ_)
			.on(UserPayment.USER_PAYMENT.QUIZ_ID.eq(Quiz.QUIZ_.ID))
			.where(UserPayment.USER_PAYMENT.STATUS.eq("Y")).and(Quiz.QUIZ_.QUIZ_TYPE.eq(quizType)).and(UserPayment.USER_PAYMENT.USER_ID.eq(userId)).fetchInto(Integer.class);
		
		return paymentIdList;
	}
}
