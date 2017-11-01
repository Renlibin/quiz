package cn.clubox.quiz.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult;
import cn.clubox.quiz.service.api.QuizResultGenerator;
import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;
import cn.clubox.quiz.service.impl.dao.QuizEngagementResultDaoExt;

@Service
public class DefaultQuizResultGenerator implements QuizResultGenerator {


	@Autowired
	private QuizEngagementResultDaoExt quizEngagementResultDao;
	
	@Override
	public Map<String,Short> retrieveQuizEngagementResult(Integer engagementId, Integer userId, String quizType) {
		
		if(QUIZ_TYPE.MBTI.value.equals(quizType)){
			//The result of MBTI quiz should be handled by MbtiQuizResultGenerator  
			return new HashMap<>(0);
		}
		
		List<QuizEngagementResult> quizEngagementResultList;

		if(engagementId != null){
			quizEngagementResultList = quizEngagementResultDao.fetchByQuizEngagementId(engagementId);
		}else{
			quizEngagementResultList = quizEngagementResultDao.fetchQuizEngagementByUserIdAndQuizType(userId, quizType);
		}
		
		Map<String,Short> resultMap = new HashMap<>(quizEngagementResultList.size());
		
		for(QuizEngagementResult result : quizEngagementResultList){
			Short tempScore = resultMap.get(result.getResultOption());
			if(tempScore != null){
				resultMap.put(result.getResultOption(), (short)(tempScore + result.getScore()));
			}else{
				resultMap.put(result.getResultOption(), result.getScore());
			}
		}
		
		return resultMap;
	}
}
