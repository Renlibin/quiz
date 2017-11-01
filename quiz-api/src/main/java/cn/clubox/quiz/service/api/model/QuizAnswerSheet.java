package cn.clubox.quiz.service.api.model;

import java.util.List;

public class QuizAnswerSheet {

	private int    userId;
	private int    quizId;
	private int    engagementId;
	private int    page;
	private int    pageSize;
	private boolean lastPage;
	private String duration;
	private List<Question> questionList;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getQuizId() {
		return quizId;
	}
	public void setQuizId(int quizId) {
		this.quizId = quizId;
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
	public int getEngagementId() {
		return engagementId;
	}
	public void setEngagementId(int engagementId) {
		this.engagementId = engagementId;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public boolean isLastPage() {
		return lastPage;
	}
	public void setLastPage(boolean lastPage) {
		this.lastPage = lastPage;
	}
}
