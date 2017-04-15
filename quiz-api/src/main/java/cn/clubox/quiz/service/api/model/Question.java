package cn.clubox.quiz.service.api.model;

import java.util.List;

public class Question {

	private int    id;
	private String title;
	private String image;
    private List<Option> optionList;
    private int selectedOptionKey;
    
    public Question(){

    }
    
    private Question(Builder builder){
    	this.id = builder.getId();
    	this.title = builder.getTitle();
    	this.image = builder.getImage();
    	this.optionList = builder.getOptionList();
    }
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<Option> getOptionList() {
		return optionList;
	}
	public void setOptionList(List<Option> optionList) {
		this.optionList = optionList;
	}
	public int getSelectedOptionKey() {
		return selectedOptionKey;
	}
	public void setSelectedOptionKey(int selectedOptionKey) {
		this.selectedOptionKey = selectedOptionKey;
	}
	
	public static class Builder{
		private int id;
		private String title;
		private String image;
		private List<Option> optionList;
		
		public Builder id(int id){
			this.id = id;
			return this;
		}
		
		public Builder title(String title){
			this.title = title;
			return this;
		}
		
		public Builder image(String image){
			this.image = image;
			return this;
		}
		
		public Builder optionList(List<Option> optionList){
			this.optionList = optionList;
			return this;
		}
		
		public int getId() {
			return id;
		}

		public String getTitle() {
			return title;
		}

		public String getImage() {
			return image;
		}

		public List<Option> getOptionList() {
			return optionList;
		}

		public Question build(){
			return new Question(this);
		}
	}
	
	@Override
	public String toString() {
		return "Question [id=" + id + ", title=" + title + ", image=" + image + ", optionList=" + optionList
				+ ", selectedOptionKey=" + selectedOptionKey + "]";
	}
}
