package cn.clubox.quiz.service.api.util;

public enum Status {

	NORMAL("N"),DELETED("D"),COMPLETE("C"),UNCOMPLETED("U");
	
	String value;
	
	private Status(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}
