package cn.clubox.quiz.service.api;

import java.util.List;

import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.QuizExtension;
import cn.clubox.quiz.service.api.model.QuizExtension.QUIZ_DOABLE_ACTION;
import cn.clubox.quiz.service.api.util.PagedModel;

public interface QuizManager {
	
	public List<Integer> retrieveUndoneQuizId(int userId);
	
	public List<QuizExtension> retrieveUndoneQuiz(int userId, boolean setDoableAction, boolean countQuizParticipant);
	
	public List<Integer> retrieveEngagedQuizId(int userId);
	
	public List<QuizExtension> retrieveEngagedQuiz(int userId, boolean setDoableAction, boolean countQuizParticipant);
	
	public Integer retrieveQuizIdBySrc(String quizSrc);
	
//	/**
//	 * Depending on the type of quiz, the result could be one or multiple entries
//	 * @param engagementId
//	 * @return
//	 */
//	public Map<String,Short> retrieveQuizEngagementResult(int engagementId);
//	
//	/**
//	 * Depending on the type of quiz, the result could be one or multiple entries
//	 * @param userId
//	 * @param quizType
//	 * @return
//	 */
//	public Map<String,Short> retrieveQuizEngagementResult(int userId, String quizType);
	
	public boolean hasPrivilige(int userId, String quizType);
	
	public List<QuizExtension> retrieveAllQuiz(int userId,boolean avilableActionDecision,
			boolean countQuizParticipant, QUIZ_DOABLE_ACTION doableAction);
	
	public QuizExtension retrieveQuizByType(int userId,boolean avilableActionDecision,
			boolean countQuizParticipant, QUIZ_DOABLE_ACTION doableAction, String quizType);
	
	public PagedModel<? extends Question> retrievePagedQuestionModel(String quizType, Integer engagementId, int page, int pageSize);

}
