package cn.clubox.quiz.service.impl.dao;

import java.util.List;

import javax.annotation.PostConstruct;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.quiz.jooq.domain.tables.UserPayment;
import cn.clubox.quiz.jooq.domain.tables.daos.UserPaymentDao;

@Repository
public class UserPaymentDaoExt extends UserPaymentDao {

	@Autowired
	private DSLContext context;
	
	@PostConstruct
	public void config(){
		super.setConfiguration(context.configuration());
	}
	
	public List<Integer> fetchPayedQuizId(int userId){
		
		List<Integer> payedQuizIdList = context.selectDistinct(UserPayment.USER_PAYMENT.QUIZ_ID).from(UserPayment.USER_PAYMENT)
			.where(UserPayment.USER_PAYMENT.USER_ID.eq(userId)).fetchInto(Integer.class);
		return payedQuizIdList;
	}
}
