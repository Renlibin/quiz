package cn.clubox.quiz.service.api;

import cn.clubox.quiz.service.api.model.QuestionsModel;

/**
 * 
 * @author Renlibin
 *
 */
public interface QuizAnswerSheetProcessor {
	
	public String getQuizName();

	public void process(QuestionsModel questionModel);
	
}
