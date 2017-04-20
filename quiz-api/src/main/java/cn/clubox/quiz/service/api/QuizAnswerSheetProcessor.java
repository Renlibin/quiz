package cn.clubox.quiz.service.api;

import cn.clubox.quiz.service.api.model.QuizAnswerSheet;

/**
 * 
 * @author Renlibin
 *
 */
public interface QuizAnswerSheetProcessor {
	
	public String getQuizName();

	public void process(QuizAnswerSheet quizAnswerSheet);
	
}
