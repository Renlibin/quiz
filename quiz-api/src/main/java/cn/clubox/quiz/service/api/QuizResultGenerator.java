package cn.clubox.quiz.service.api;

import java.util.Map;

public interface QuizResultGenerator {

	/**
	 * Depending on the type of quiz, the result could be one or multiple entries
	 *  
	 * @param engagementId
	 * @param userId
	 * @param quizType
	 * @return
	 */
	public Map<String,Short> retrieveQuizEngagementResult(Integer engagementId, Integer userId, String quizType);
	
	
}
