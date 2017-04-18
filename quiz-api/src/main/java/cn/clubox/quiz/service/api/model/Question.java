package cn.clubox.quiz.service.api.model;

import java.util.List;

public class Question {

	private int  id;  
	private short  sequenceNumber;
	private String title;
	private String image;
    private List<Option> optionList;
    private short selectedOptionKey;
    
    public Question(){

    }
    
    private Question(Builder builder){
    	this.id = builder.getId();
    	this.sequenceNumber = builder.getSequenceNumber();
    	this.title = builder.getTitle();
    	this.image = builder.getImage();
    	this.optionList = builder.getOptionList();
    }
    
	public int getId() {
		return id;
	}

	public short getSequenceNumber() {
		return sequenceNumber;
	}
	
    public void setSequenceNumber(short sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	//	public void setId(short id) {
//		this.sequenceNumber = id;
//	}
	public String getTitle() {
		return title;
	}
//	public void setTitle(String title) {
//		this.title = title;
//	}
	public String getImage() {
		return image;
	}
//	public void setImage(String image) {
//		this.image = image;
//	}
	public List<Option> getOptionList() {
		return optionList;
	}
//	public void setOptionList(List<Option> optionList) {
//		this.optionList = optionList;
//	}
	public short getSelectedOptionKey() {
		return selectedOptionKey;
	}
	public void setSelectedOptionKey(short selectedOptionKey) {
		this.selectedOptionKey = selectedOptionKey;
	}
	
	public static class Builder{
		private int   id;
		private short sequenceNumber;
		private String title;
		private String image;
		private List<Option> optionList;
		
		public Builder id(int id){
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
		return "Question [id=" + id + ", sequenceNumber=" + sequenceNumber + ", title=" + title + ", image=" + image
				+ ", optionList=" + optionList + ", selectedOptionKey=" + selectedOptionKey + "]";
	}
}
