package cn.clubox.quiz.service.api.util;

public enum QuizEngagementStatus {

	COMPLETE("C"),UNCOMPLETED("U");
	
	String value;
	
	private QuizEngagementStatus(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}
