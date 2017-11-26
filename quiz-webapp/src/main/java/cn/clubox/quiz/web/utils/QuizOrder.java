package cn.clubox.quiz.web.utils;

public class QuizOrder {

	private Integer userId;
	private String  quizType;
	
	public QuizOrder(Integer userId,String quizType){
		this.userId = userId;
		this.quizType = quizType;
	}

	public Integer getUserId() {
		return userId;
	}
	public String getQuizType() {
		return quizType;
	}

	@Override
	public String toString() {
		return "QuizOrder [getUserId()=" + getUserId()  + ", getQuizType()="
				+ getQuizType() + "]";
	}
	
}
