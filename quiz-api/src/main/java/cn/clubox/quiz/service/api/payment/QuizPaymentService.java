package cn.clubox.quiz.service.api.payment;

import java.math.BigDecimal;

public interface QuizPaymentService {

	public boolean isPaid(String userId, int quizId);
	
	public boolean purchase(String userId, int quizId, BigDecimal price);
}
