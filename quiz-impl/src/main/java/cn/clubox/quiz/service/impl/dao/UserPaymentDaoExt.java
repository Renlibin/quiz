package cn.clubox.quiz.service.impl.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.quiz.jooq.domain.tables.daos.UserPaymentDao;
import cn.clubox.quiz.service.api.util.Status;

import static cn.clubox.quiz.jooq.domain.tables.Quiz.QUIZ_;
import static cn.clubox.quiz.jooq.domain.tables.UserPayment.USER_PAYMENT;
import static cn.clubox.quiz.jooq.domain.tables.QuizEngagement.QUIZ_ENGAGEMENT;

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
		
		List<Integer> payedQuizIdList = context.selectDistinct(USER_PAYMENT.QUIZ_ID).from(USER_PAYMENT)
			.where(USER_PAYMENT.USER_ID.eq(userId).and(USER_PAYMENT.STATUS.eq(Status.COMPLETE.getValue())))
				.fetchInto(Integer.class);
		
		if(logger.isDebugEnabled()){
			logger.debug("Payed quiz ids are {}", new HashSet<Integer>(payedQuizIdList).toString());
		}
		
		return payedQuizIdList;
	}
	
	public boolean isQuizPaid(Integer userId, Integer quizId){
		
		Integer paidQuizId = context.select(USER_PAYMENT.QUIZ_ID).from(USER_PAYMENT)
		.where(USER_PAYMENT.USER_ID.equal(userId).and(USER_PAYMENT.QUIZ_ID.equal(quizId))
				.and(USER_PAYMENT.STATUS.equal(Status.COMPLETE.getValue())))
			.fetchOneInto(Integer.class);
		
		if(Objects.nonNull(paidQuizId)){
			return true;
		}
		
		return false;
	}
	
	public List<Integer> fetchUndoneQuizId(int userId){
		
		List<Integer> undoneQuizIdList = context.select(USER_PAYMENT.QUIZ_ID).from(USER_PAYMENT)
			.leftJoin(QUIZ_ENGAGEMENT).on(USER_PAYMENT.QUIZ_ID.eq(QUIZ_ENGAGEMENT.QUIZ_ID))
			.where(USER_PAYMENT.USER_ID.eq(userId).and(USER_PAYMENT.STATUS.eq(Status.COMPLETE.getValue()))
					.and(QUIZ_ENGAGEMENT.QUIZ_ID.isNull()
							.or(QUIZ_ENGAGEMENT.STATUS.eq(Status.UNCOMPLETED.getValue()))))
				.fetchInto(Integer.class);
		
		if(logger.isDebugEnabled()){
			logger.debug("Undone quiz ids are {}", new HashSet<Integer>(undoneQuizIdList).toString());
		}
		
		return undoneQuizIdList;
	}
	
	public List<Integer> fetchUserPaymentByUserIdAndQuizType(int userId, String quizType){
		
		List<Integer> paymentIdList = context.select(USER_PAYMENT.ID).from(USER_PAYMENT).innerJoin(QUIZ_)
			.on(USER_PAYMENT.QUIZ_ID.eq(QUIZ_.ID))
			.where(USER_PAYMENT.STATUS.eq(Status.COMPLETE.getValue())).and(QUIZ_.QUIZ_TYPE.eq(quizType))
				.and(USER_PAYMENT.USER_ID.eq(userId)).fetchInto(Integer.class);
		
		return paymentIdList;
	}
	
	public int updateUserPaymentTransactionId(String tradeNo, String transactionId){
		
		if(logger.isDebugEnabled()){
			logger.debug("The transaction {} is going to be updated via trade no {}", transactionId, tradeNo);
		}
		
		return context.update(USER_PAYMENT).set(USER_PAYMENT.OUT_TRANSACTION_ID, transactionId)
			.set(USER_PAYMENT.STATUS, Status.COMPLETE.getValue())
			.where(USER_PAYMENT.TRADE_NO.eq(tradeNo)).execute();
		
	}
}
