package cn.clubox.quiz.service.api.model;

import java.math.BigDecimal;

public class Quiz {

	private int    id;
	private String name;
	private String title;
	private String description;
	private BigDecimal price;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public enum QUIZ_TYPE {
		zym("zym");
		
		public String value;
		
		private QUIZ_TYPE(String value){
			this.value = value;
		}
		
		public static QUIZ_TYPE getByValue(String value){
			
			for(QUIZ_TYPE type : QUIZ_TYPE.values()){
				if(type.value.equals(value)){
					return type;
				}
			}
			
			return null;
		}
	}
}
