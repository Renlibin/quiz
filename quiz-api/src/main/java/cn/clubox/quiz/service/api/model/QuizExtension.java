package cn.clubox.quiz.service.api.model;

public class QuizExtension {

	private Quiz quiz;
	private int  numberOfParticipant;
	private QUIZ_AVILABLE_ACTION avilableActionTitle;
	private String avilableActionLink;

	public Quiz getQuiz() {
		return quiz;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
	
	public int getNumberOfParticipant() {
		return numberOfParticipant;
	}

	public void setNumberOfParticipant(int numberOfParticipant) {
		this.numberOfParticipant = numberOfParticipant;
	}

	public QUIZ_AVILABLE_ACTION getAvilableActionTitle() {
		return avilableActionTitle;
	}

	public void setAvilableActionTitle(QUIZ_AVILABLE_ACTION avilableActionTitle) {
		this.avilableActionTitle = avilableActionTitle;
	}

	public String getAvilableActionLink() {
		return avilableActionLink;
	}

	public void setAvilableActionLink(String avilableActionLink) {
		this.avilableActionLink = avilableActionLink;
	}

	public enum QUIZ_AVILABLE_ACTION {

		ENGAGEMENT("开始答题"), PAYNOW("立即购买"), SHOWRESULT("查看结果");

		public String value;
		
		private QUIZ_AVILABLE_ACTION(String value) {
			this.value = value;
		}
		
		public String getValue(){
			return this.value;
		}
	}
}
