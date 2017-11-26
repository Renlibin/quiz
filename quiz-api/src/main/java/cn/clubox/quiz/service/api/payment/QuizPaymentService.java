package cn.clubox.quiz.service.api.payment;

import java.math.BigDecimal;
import java.util.Map;

import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;

public interface QuizPaymentService {

	public boolean isPaid(String userId, int quizId);
	
	public Map<String,String> prePayment(String openId,  QUIZ_TYPE quizType) throws Exception;
	
	public boolean purchase(String userId, int quizId, BigDecimal price);
}
