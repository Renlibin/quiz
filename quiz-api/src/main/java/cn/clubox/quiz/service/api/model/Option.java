package cn.clubox.quiz.service.api.model;

public class Option {

	private short optionOrderNumber;
    private String  optionText;
    private int   optionValue;
    
	public int getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(int optionValue) {
		this.optionValue = optionValue;
	}
	
	public short getOptionOrderNumber() {
		return optionOrderNumber;
	}
	public void setOptionOrderNumber(short optionOrderNumber) {
		this.optionOrderNumber = optionOrderNumber;
	}
	public String getOptionText() {
		return optionText;
	}
	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}
}
