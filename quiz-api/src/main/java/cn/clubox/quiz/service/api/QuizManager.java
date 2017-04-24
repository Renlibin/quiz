package cn.clubox.quiz.service.api;

import java.util.List;
import java.util.Map;

import cn.clubox.quiz.service.api.model.Quiz;
import cn.clubox.quiz.service.api.model.QuizExtension;
import cn.clubox.quiz.service.api.model.QuizExtension.QUIZ_DOABLE_ACTION;

public interface QuizManager {

//	public Map<String,Quiz> retrieveAllQuiz();
//	
//	public Map<String, List<Question>> retrieveAllQuizQuestion();
	
	public List<Integer> retrieveUndoneQuizId(int userId);
	
	public List<QuizExtension> retrieveUndoneQuiz(int userId, boolean setDoableAction, boolean countQuizParticipant);
	
	public List<Integer> retrieveEngagedQuizId(int userId);
	
	public List<QuizExtension> retrieveEngagedQuiz(int userId, boolean setDoableAction, boolean countQuizParticipant);
	
	public Quiz retrieveQuiz(String username, String quizType);
	
//	public List<QuizExtension> doableActionDecision(int userId, Quiz ... quizs);
	
//	public int countQuizParticipant(int quizId);
	
	/**
	 * Depending on the type of quiz, the result could be one or multiple entries
	 * @param engagementId
	 * @return
	 */
	public Map<String,Integer> retrieveQuizEngagementResult(int engagementId);
	
	/**
	 * Depending on the type of quiz, the result could be one or multiple entries
	 * @param userId
	 * @param quizType
	 * @return
	 */
	public Map<String,Integer> retrieveQuizEngagementResult(int userId, String quizType);
	
	public boolean hasPrivilige(int userId, String quizType);
	
	public List<QuizExtension> retrieveAllQuiz(int userId,boolean avilableActionDecision,
			boolean countQuizParticipant, QUIZ_DOABLE_ACTION doableAction);
	
	public QuizExtension retrieveQuizByType(int userId,boolean avilableActionDecision,
			boolean countQuizParticipant, QUIZ_DOABLE_ACTION doableAction, String quizType);

}
