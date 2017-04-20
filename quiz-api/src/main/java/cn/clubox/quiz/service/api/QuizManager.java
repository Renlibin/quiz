package cn.clubox.quiz.service.api;

import java.util.List;
import java.util.Map;

import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.Quiz;
import cn.clubox.quiz.service.api.model.QuizExtension;

public interface QuizManager {

	public Map<String,Quiz> retrieveAllQuiz();
	
	public Map<String, List<Question>> retrieveAllQuizQuestion();
	
	public Quiz retrieveQuiz(String username, String quizType);
	
	public List<QuizExtension> avilableActionDecision(int userId, Quiz ... quizs);
}
