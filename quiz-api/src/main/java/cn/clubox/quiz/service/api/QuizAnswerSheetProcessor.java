package cn.clubox.quiz.service.api;

import cn.clubox.quiz.service.api.model.QuizAnswerSheet;

/**
 * 
 * @author Renlibin
 *
 */
public interface QuizAnswerSheetProcessor {
	
	public String getQuizName();

	/**
	 * 
	 * @param quizAnswerSheet
	 * @return The quiz engagement id will be returned
	 */
	public int process(QuizAnswerSheet quizAnswerSheet);
	
}
