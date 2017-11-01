package cn.clubox.quiz.service.api.model;

import java.util.List;

public class Question {

	private int  id;
	private int  engagementResultId;
	private short  sequenceNumber;
	private String title;
	private String image;
    private List<Option> optionList;
    private short selectedOptionKey;
    private short selectedOptionValue = -100;
    
    public Question(){

    }
    
    private Question(Builder builder){
    	this.sequenceNumber = builder.getSequenceNumber();
    	this.title = builder.getTitle();
    	this.image = builder.getImage();
    	this.optionList = builder.getOptionList();
    	this.id = builder.getId();
    }
    
	public int getEngagementResultId() {
		return engagementResultId;
	}

	public void setEngagementResultId(int engagementResultId) {
		this.engagementResultId = engagementResultId;
	}

	public short getSequenceNumber() {
		return sequenceNumber;
	}
	
    public void setSequenceNumber(short sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

    public int getId(){
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

	public short getSelectedOptionKey() {
		return selectedOptionKey;
	}
	public void setSelectedOptionKey(short selectedOptionKey) {
		this.selectedOptionKey = selectedOptionKey;
	}
	
	public short getSelectedOptionValue() {
		return selectedOptionValue;
	}
	
	public void setSelectedOptionValue(short selectedOptionValue) {
		this.selectedOptionValue = selectedOptionValue;
	}

	public static class Builder{
		private int   id;
		private short sequenceNumber;
		private String title;
		private String image;
		private List<Option> optionList;
		
		public Builder  id(int id){
			this.id = id;
			return this;
		}
		
		public Builder sequenceNumber(short sequenceNumber){
			this.sequenceNumber = sequenceNumber;
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
		
		public int getId(){
			return id;
		}
		
		public short getSequenceNumber() {
			return sequenceNumber;
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
		return "Question [getEngagementResultId()=" + getEngagementResultId() + ", getSequenceNumber()="
				+ getSequenceNumber() + ", getId()=" + getId() + ", getTitle()=" + getTitle() + ", getImage()="
				+ getImage() + ", getOptionList()=" + getOptionList() + ", getSelectedOptionKey()="
				+ getSelectedOptionKey() + ", getSelectedOptionValue()=" + getSelectedOptionValue() + "]";
	}
	
}
