package cn.clubox.quiz.service.api.payment;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Map;

import cn.clubox.quiz.service.api.QuizOrder;

public interface QuizPaymentService {

	public boolean isPaid(Integer userId, int quizId);
	
	public Map<String,String> prePayment(Integer userId,String openId,  QuizOrder quizOrder) throws Exception;
	
	public boolean purchase(String userId, int quizId, BigDecimal price);
	
	public String updatePaymentTransactionId(InputStream inStream);
}
