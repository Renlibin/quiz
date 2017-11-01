package cn.clubox.quiz.service.api;

import java.util.List;

import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.QuizAnswerSheet;

/**
 * 
 * @author Renlibin
 *
 */
public interface QuizAnswerSheetProcessor<T> {
	
	public String getQuizName();

	/**
	 * 
	 * @param quizAnswerSheet
	 * @return The quiz engagement id will be returned
	 */
	public int process(QuizAnswerSheet quizAnswerSheet);
	
	/**
	 * 
	 * @param quizAnswerSheet
	 * @return
	 */
	public int persistQuizEngagement(QuizAnswerSheet quizAnswerSheet);
	
	/**
	 * 
	 * @param <T>
	 * @param quizEngagementId
	 * @param score
	 */
//	@Deprecated
//	public void  persistQuizEngagementResult(int quizEngagementId, T score);
	
	public void  persistQuizEngagementResult(int quizEngagementId, List<? extends Question> questions);
	
	/**
	 * 
	 * @param questions
	 * @return
	 */
//	@Deprecated
//	public T countTotalScore(List<Question> questions);
	
	/****** Interface ******/
	
	public interface ScoringRule<T> {
		@Deprecated
		boolean scoring(T score, Question question);
		
		String getResultOption(Question question);
	}
	
	public String getResultOption(Question question);
	
}
