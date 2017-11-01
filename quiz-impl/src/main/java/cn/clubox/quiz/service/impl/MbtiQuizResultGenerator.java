package cn.clubox.quiz.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult;
import cn.clubox.quiz.service.api.QuizResultGenerator;
import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;
import cn.clubox.quiz.service.impl.dao.QuizEngagementResultDaoExt;

@Service
public class MbtiQuizResultGenerator implements QuizResultGenerator {
	
	private static Logger logger = LoggerFactory.getLogger(MbtiQuizResultGenerator.class);
	
	@Autowired
	private QuizEngagementResultDaoExt quizEngagementResultDao;

	@Override
	public Map<String,Short> retrieveQuizEngagementResult(Integer engagementId, Integer userId, String quizType) {
		
		if(QUIZ_TYPE.MBTI.value.equals(quizType) == false){
			//MbtiQuizResultGenerator only handle MBTI quiz
			return new HashMap<>(0);
		}
		
		List<QuizEngagementResult> quizEngagementResultList;
		
		if(logger.isDebugEnabled()){
			logger.debug("The engagement id is {}", engagementId);
		}
		
		if(engagementId != null){
			quizEngagementResultList = quizEngagementResultDao.fetchByQuizEngagementId(engagementId);
		}else{
			quizEngagementResultList = quizEngagementResultDao.fetchQuizEngagementByUserIdAndQuizType(userId, quizType);
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("quizEngagementResultList is {}", quizEngagementResultList);
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
		
		return this.processing(resultMap);
	}
	
	private Map<String,Short> processing(Map<String,Short> resultMap){
		
		if(logger.isDebugEnabled()){
			logger.debug("Processing MBTI Quiz result");
		}
		
		Map<String,Short> finalResultMap = new LinkedHashMap<String,Short>();
		
		/** 
		* E 69
		* I 73
		* S 83
		* N 78
		* T 84
		* F 70
		* J 74
		* P 80
		 */
		
		for(RESULT_DIMENSION rd : RESULT_DIMENSION.values()){
			String finalOption = this.getFinalOption(resultMap, rd.getOption1(), rd.getOption2());
			if(finalOption != null){
			    Integer finalValue = (resultMap.get(finalOption) / 15) % 100;
			    
			    if(logger.isDebugEnabled()){
			    	logger.debug("The final value of option {} is {}", finalOption, finalValue);
			    }
			    
				finalResultMap.put(finalOption, finalValue.shortValue());
			}
		}
		
		return finalResultMap;
	}
	
	private String getFinalOption(Map<String,Short> resultMap, String a, String b){
		
		if(resultMap.get(a) == null){
			return b;
		}
		if(resultMap.get(b) == null){
			return a;
		}
		return resultMap.get(a) > resultMap.get(b) ? a : b;
	}
	
	enum RESULT_DIMENSION {
		
	    SOURCE("E","I"),INFORMATION("S","N"),DECISION("T","F"),PLANING("J","P");
		
		String option1;
		String option2;
		
		RESULT_DIMENSION(String option1, String option2){
			this.option1 = option1;
			this.option2 = option2;
		}
		
		String getOption1(){
			return option1;
		}
		
		String getOption2(){
			return option2;
		}
		
	}

}
