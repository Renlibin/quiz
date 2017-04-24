package cn.clubox.quiz.service.api.model;

public class QuizExtension {

	private Quiz quiz;
	private int  numberOfParticipant;
	private QUIZ_DOABLE_ACTION doableActionTitle;
	private String doableActionLink;

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

	public QUIZ_DOABLE_ACTION getDoableActionTitle() {
		return doableActionTitle;
	}

	public void setDoableActionTitle(QUIZ_DOABLE_ACTION doableActionTitle) {
		this.doableActionTitle = doableActionTitle;
	}

	public String getDoableActionLink() {
		return doableActionLink;
	}

	public void setDoableActionLink(String doableActionLink) {
		this.doableActionLink = doableActionLink;
	}

	public enum QUIZ_DOABLE_ACTION {

		ENGAGEMENT("engagement"){
			@Override
			public String toString(){
				return "开始答题";
			}
		},
		BUYNOW("buynow"){
			@Override
			public String toString(){
				return "立即购买";
			}
		}, 
		PAYNOW("paynow"){
			@Override
			public String toString(){
				return "立即购买";
			}
		},
		SHOWRESULT("result"){
			@Override
			public String toString(){
				return "查看结果";
			}
		},PAYMENT("payment"){
			public String toString(){
				return "立即支付";
			}
		};

		public String value;
		
		private QUIZ_DOABLE_ACTION(String value) {
			this.value = value;
		}
		
		public String getValue(){
			return this.value;
		}
	}
}
