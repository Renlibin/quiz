package cn.clubox.quiz.service.api;

import java.util.List;

import cn.clubox.quiz.service.api.model.Question;

/**
 * 
 * @author Renlibin
 *
 */
public interface QuizAnswerSheetProcessor {

	public void process(List<Question> questions);
	
}