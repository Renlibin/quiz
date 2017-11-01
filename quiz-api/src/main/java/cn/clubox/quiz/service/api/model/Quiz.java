package cn.clubox.quiz.service.api.model;

import java.math.BigDecimal;
import java.util.List;

import cn.clubox.quiz.service.api.util.PagedListHolder;

public class Quiz {

	private int id;
	private String name;
	private String title;
	private String description;
	private BigDecimal price;
	private BigDecimal originalPrice;
	private String logoSrc;
	private String quizType;
	private List<Question> questionList;
	
	private PagedListHolder<? extends Question> pagedListHolder;
	
	public Quiz(){

	}
	
	private Quiz(Builder builder){
		this.id = builder.getId();
		this.name = builder.getName();
		this.title = builder.getTitle();
		this.description = builder.getDescription();
		this.price = builder.getPrice();
		this.originalPrice = builder.getOriginalPrice();
		this.logoSrc = builder.getLogoSrc();
		this.quizType = builder.getQuizType();
		this.questionList = builder.getQuestionList();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public BigDecimal getPrice() {
		return price;
	}
	
	public BigDecimal getOriginalPrice(){
		return originalPrice;
	}

	public String getLogoSrc() {
		return logoSrc;
	}

	public String getQuizType() {
		return quizType;
	}

	public List<Question> getQuestionList() {
		return questionList;
	}
	
	public PagedListHolder<? extends Question> getPagedListHolder() {
		return pagedListHolder;
	}
	
	public void setPagedListHolder(PagedListHolder<? extends Question> pagedQuestionHolder) {
		this.pagedListHolder = pagedQuestionHolder;
	}

	public static class Builder {
		
		private int id;
		private String name;
		private String title;
		private String description;
		private BigDecimal price;
		private BigDecimal originalPrice;
		private String logoSrc;
		private String quizType;
		private List<Question> questionList;
//		private PagedListHolder<? extends Question> pagedQuestionHolder;
		
		public Builder setId(int id){
			this.id = id;
			return this;
		}
		
		public Builder setName(String name){
			this.name = name;
			return this;
		}
		
		public Builder setTitle(String title){
			this.title = title;
			return this;
		}
		
		public Builder setDescription(String description){
			this.description = description;
			return this;
		}
		
		public Builder setPrice(BigDecimal price){
			this.price = price;
			return this;
		}
		
		public Builder setOriginalPrice(BigDecimal originalPrice){
			this.originalPrice = originalPrice;
			return this;
		}
		
		public Builder setLogoSrc(String logoSrc){
			this.logoSrc = logoSrc;
			return this;
		}
		
		public Builder setQuizType(String quizType){
			this.quizType = quizType;
			return this;
		}
		
		public Builder setQuestionList(List<Question> questionList){
			this.questionList = questionList;
			return this;
		}
		
		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getTitle() {
			return title;
		}

		public String getDescription() {
			return description;
		}

		public BigDecimal getPrice() {
			return price;
		}
		
		public BigDecimal getOriginalPrice(){
			return originalPrice;
		}

		public String getLogoSrc() {
			return logoSrc;
		}

		public String getQuizType() {
			return quizType;
		}

		public List<Question> getQuestionList() {
			return questionList;
		}

		public Quiz build(){
			return new Quiz(this);
		}
	}

	public enum QUIZ_TYPE {
		ZYM("zym"),ISEQ("iseq"),CONFIDENCE("confidence"),
		SOCIALACC("socialacc"),CAREERCAPABILITY("careercapability"),MBTI("mbti");

		public String value;

		private QUIZ_TYPE(String value) {
			this.value = value;
		}

		public static QUIZ_TYPE getByValue(String value) {

			for (QUIZ_TYPE type : QUIZ_TYPE.values()) {
				if (type.value.equals(value)) {
					return type;
				}
			}

			return null;
		}
	}
}
