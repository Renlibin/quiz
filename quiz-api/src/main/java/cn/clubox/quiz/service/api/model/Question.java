package cn.clubox.quiz.service.api.model;

import java.util.List;

public class Question {

	private int    id;
	private String title;
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
		return "Question [id=" + id + ", title=" + title + ", optionList=" + optionList + ", selectedOptionKey="
				+ selectedOptionKey + "]";
	}
}
