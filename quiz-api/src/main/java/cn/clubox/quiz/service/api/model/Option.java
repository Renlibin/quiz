package cn.clubox.quiz.service.api.model;

public class Option {

	private Integer optionOrderNumber;
    private String  optionText;
    private short   optionValue;
    
	public short getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(short optionValue) {
		this.optionValue = optionValue;
	}
	
	public Integer getOptionOrderNumber() {
		return optionOrderNumber;
	}
	public void setOptionOrderNumber(Integer optionOrderNumber) {
		this.optionOrderNumber = optionOrderNumber;
	}
	public String getOptionText() {
		return optionText;
	}
	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}
}
