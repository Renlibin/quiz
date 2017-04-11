package cn.clubox.quiz.service.api;

import java.util.List;

import cn.clubox.quiz.service.api.model.Question;

public interface QuizQuestionGenerator {

	public QuizQuestion generate();
	
	public static class QuizQuestion {
		
		private String quiz;
		private List<Question> questionList;
		
		public QuizQuestion(String quiz, List<Question> questionList){
			this.quiz = quiz;
			this.questionList = questionList;
		}

		public String getQuiz() {
			return quiz;
		}

		public List<Question> getQuestionList() {
			return questionList;
		}
	}
}
