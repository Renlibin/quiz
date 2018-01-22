package cn.clubox.quiz.service.api;

public class QuizOrder {

	private Integer userId;
	private String  quizType;
	private String  quizSrc;
	
	public QuizOrder(Integer userId,String quizType,String quizSrc){
		this.userId = userId;
		this.quizType = quizType;
		this.quizSrc = quizSrc;
	}

	public Integer getUserId() {
		return userId;
	}
	public String getQuizType() {
		return quizType;
	}
	public String getQuizSrc() {
		return quizSrc;
	}

	@Override
	public String toString() {
		return "QuizOrder [userId=" + userId + ", quizType=" + quizType + ", quizSrc=" + quizSrc + "]";
	}

}
