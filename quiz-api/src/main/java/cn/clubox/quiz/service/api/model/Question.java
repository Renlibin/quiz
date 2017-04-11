package cn.clubox.quiz.service.api.model;

import java.util.List;

public class Question {

	private int    id;
	private String title;
	private String image;
    private List<Option> optionList;
    private int selectedOptionKey;
    
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
	@Override
	public String toString() {
		return "Question [id=" + id + ", title=" + title + ", image=" + image + ", optionList=" + optionList
				+ ", selectedOptionKey=" + selectedOptionKey + "]";
	}
}
