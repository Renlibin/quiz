package cn.clubox.quiz.service.api;

import java.util.List;

import cn.clubox.quiz.service.api.model.Question;

public interface QuizQuestionGenerator {

	public QuizQuestion generate();
	
	public static class QuizQuestion {
		
		private int    quizId;
		private String quizType;
		private List<Question> questionList;
		
		public QuizQuestion(int quizId, String quizType, List<Question> questionList){
			this.quizId = quizId;
			this.quizType = quizType;
			this.questionList = questionList;
		}
		
		public int getQuizId() {
			return quizId;
		}

		public void setQuizId(int quizId) {
			this.quizId = quizId;
		}

		public String getQuizType() {
			return quizType;
		}

		public void setQuizType(String quizType) {
			this.quizType = quizType;
		}

		public List<Question> getQuestionList() {
			return questionList;
		}
	}
}
