package cn.clubox.quiz.service.api.model;

public class Option {

	private String optionOrderNumber;
    private String  optionText;
    private int   optionValue;
    
    public Option(String optionOrderNumber, String optionText, int optionValue){
    	this.optionOrderNumber = optionOrderNumber;
    	this.optionText = optionText;
    	this.optionValue = optionValue;
    }
    
	public int getOptionValue() {
		return optionValue;
	}
//	public void setOptionValue(int optionValue) {
//		this.optionValue = optionValue;
//	}
	
	public String getOptionOrderNumber() {
		return optionOrderNumber;
	}
//	public void setOptionOrderNumber(String optionOrderNumber) {
//		this.optionOrderNumber = optionOrderNumber;
//	}
	public String getOptionText() {
		return optionText;
	}
//	public void setOptionText(String optionText) {
//		this.optionText = optionText;
//	}
}
