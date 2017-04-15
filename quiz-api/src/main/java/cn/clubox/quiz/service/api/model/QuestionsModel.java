package cn.clubox.quiz.service.api.model;

import java.util.List;

public class QuestionsModel {
	
	private int quizId;
    private int userId;
    private String duration;
    
	private List<Question> questionList;
	
	public int getQuizId() {
		return quizId;
	}
	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public List<Question> getQuestionList() {
		return questionList;
	}
	public void setQuestionList(List<Question> questionList) {
		this.questionList = questionList;
	}
}
