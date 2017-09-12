package cn.clubox.quiz.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagement;
import cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult;
import cn.clubox.quiz.service.api.QuizAnswerSheetProcessor;
import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;
import cn.clubox.quiz.service.api.model.QuizAnswerSheet;
import cn.clubox.quiz.service.impl.CareerCapabilityQuizAnswerSheetProcessor.CareerCapabilityScore;
import cn.clubox.quiz.service.impl.CareerCapabilityQuizAnswerSheetProcessor.CareerCapabilityScore.SCORE_OPTION;
import cn.clubox.quiz.service.impl.dao.QuizEngagementDaoExt;
import cn.clubox.quiz.service.impl.dao.QuizEngagementResultDaoExt;

@Service
public class CareerCapabilityQuizAnswerSheetProcessor implements QuizAnswerSheetProcessor<CareerCapabilityScore> {

	private static final Logger logger = LoggerFactory.getLogger(CareerCapabilityQuizAnswerSheetProcessor.class);
	
	@Autowired
	private QuizEngagementDaoExt quizEngagementDao;
	
	@Autowired
	private QuizEngagementResultDaoExt quizEngagementResultDao;
	
	@Override
	public String getQuizName() {

		return QUIZ_TYPE.CAREERCAPABILITY.value;
	}

	@Override
	public int process(QuizAnswerSheet quizAnswerSheet) {

		if(logger.isDebugEnabled()){
			logger.debug("Start to process CAREERCAPABILITY quiz answer sheet");
		}
		
		CareerCapabilityScore score = this.countTotalScore(quizAnswerSheet.getQuestionList());
		
		if(logger.isDebugEnabled()){
			logger.debug("The final score is {}", score.toString());
		}
		
		int quizEngagementId = this.persistQuizEngagement(quizAnswerSheet);
		this.persistQuizEngagementResult(quizEngagementId, score);
		
		return quizEngagementId;
	}

	@Override
	public int persistQuizEngagement(QuizAnswerSheet quizAnswerSheet) {

		if(logger.isDebugEnabled()){
			logger.debug("Persisting Quiz Engagement");
		}
		
		QuizEngagement quizEngagement = new QuizEngagement();
		quizEngagement.setQuizId(quizAnswerSheet.getQuizId());
		quizEngagement.setUserId(quizAnswerSheet.getUserId());
		quizEngagement.setDuration(quizAnswerSheet.getDuration());
		quizEngagement.setStored(new Timestamp(new Date().getTime()));
		
		int quizEngagementId = quizEngagementDao.insertWithReturning(quizEngagement);
		
		return quizEngagementId;
	}

	@Override
	public void persistQuizEngagementResult(int quizEngagementId, CareerCapabilityScore score) {

		List<QuizEngagementResult>quizEngagementResultList = new ArrayList<>();
		
		for(SCORE_OPTION sopt : CareerCapabilityScore.SCORE_OPTION.values()){
			short value = 0;
			String resultOption = sopt.value;
			
			//TO generate get method 
			String methodName = "get".concat(resultOption.substring(0, 1).toUpperCase().concat(resultOption.substring(1)).concat("Score"));
			
			try {
				Method method = score.getClass().getMethod(methodName);
				
				if(logger.isDebugEnabled()){
					logger.debug("The method {} is going to be invoked", methodName);
				}
				
				value = (short)method.invoke(score);
				
			} catch (NoSuchMethodException | SecurityException |  IllegalAccessException | InvocationTargetException e) {
				logger.error("Error message {}", e.getMessage());
			}
			
			QuizEngagementResult qer = new QuizEngagementResult();
			qer.setQuizEngagementId(quizEngagementId);
			qer.setResultOption(resultOption);
			qer.setScore((int)value); /** Score of QuizEngagementResult should be changed to short */
			qer.setStored(new Timestamp(new Date().getTime()));
			
			quizEngagementResultList.add(qer);
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Size of QuizEngagementResult is {}", quizEngagementResultList.size());
		}
		
		quizEngagementResultDao.insertMultipleRecords(quizEngagementResultList);
	}

	@Override
	public CareerCapabilityScore countTotalScore(List<Question> questions) {
		
		CareerCapabilityScore score = new CareerCapabilityScore();
		
		for(Question question : questions){
			if(isBetween(question.getSequenceNumber(),1,6)){
				score.setLanguageScore((short)(score.getLanguageScore() + question.getSelectedOptionKey()));
			}
			else if(isBetween(question.getSequenceNumber(),7,12)){
				score.setMathScore((short)(score.getMathScore() + question.getSelectedOptionKey()));
			}
			else if(isBetween(question.getSequenceNumber(),13,18)){
				score.setJudgementScore((short)(score.getJudgementScore() + question.getSelectedOptionKey()));
			}
			else if(isBetween(question.getSequenceNumber(),19,24)){
				score.setDetailScore((short)(score.getDetailScore() + question.getSelectedOptionKey()));
			}
			else if(isBetween(question.getSequenceNumber(),25,30)){
				score.setWritingScore((short)(score.getWritingScore() + question.getSelectedOptionKey()));
			}
			else if(isBetween(question.getSequenceNumber(),31,36)){
				score.setSportScore((short)(score.getSportScore() + question.getSelectedOptionKey()));
			}
			else if(isBetween(question.getSequenceNumber(),37,42)){
				score.setWorkScore((short)(score.getWorkScore() + question.getSelectedOptionKey()));
			}
			else if(isBetween(question.getSequenceNumber(),43,48)){
				score.setSocialScore((short)(score.getSocialScore() + question.getSelectedOptionKey()));
			}
			else{
				score.setOrganizeScore((short)(score.getOrganizeScore() + question.getSelectedOptionKey()));
			}
		}
		return score;
	}
	
	private boolean isBetween(short x, int lower, int upper) {
		  return lower <= x && x <= upper;
	}
	
	/****** Inner class ******/
	// 语言能力 数理能力 空间判断能力 细节分辨能力 书写能力 运动协调能力 动手能力 社会交往能力 组织管理能力

	public static class CareerCapabilityScore {
		
		private short languageScore;
		private short mathScore;
		private short judgementScore;
		private short detailScore;
		private short writingScore;
		private short sportScore;
		private short workScore;
		private short socialScore;
		private short organizeScore;
		
		public short getLanguageScore() {
			return languageScore;
		}
		public void setLanguageScore(short languageScore) {
			this.languageScore = languageScore;
		}
		public short getMathScore() {
			return mathScore;
		}
		public void setMathScore(short mathScore) {
			this.mathScore = mathScore;
		}
		public short getJudgementScore() {
			return judgementScore;
		}
		public void setJudgementScore(short judgementScore) {
			this.judgementScore = judgementScore;
		}
		public short getDetailScore() {
			return detailScore;
		}
		public void setDetailScore(short detailScore) {
			this.detailScore = detailScore;
		}
		public short getWritingScore() {
			return writingScore;
		}
		public void setWritingScore(short writingScore) {
			this.writingScore = writingScore;
		}
		public short getSportScore() {
			return sportScore;
		}
		public void setSportScore(short sportScore) {
			this.sportScore = sportScore;
		}
		public short getWorkScore() {
			return workScore;
		}
		public void setWorkScore(short workScore) {
			this.workScore = workScore;
		}
		public short getSocialScore() {
			return socialScore;
		}
		public void setSocialScore(short socialScore) {
			this.socialScore = socialScore;
		}
		public short getOrganizeScore() {
			return organizeScore;
		}
		public void setOrganizeScore(short organizeScore) {
			this.organizeScore = organizeScore;
		}
		
		public static enum SCORE_OPTION {
			
			LANGUAGE("language"),MATH("math"),JUDGEMENT("judgement"),DETAIL("detail"),
				WRITING("writing"),SPORT("sport"),WORK("work"),SOCIAL("social"),ORGANIZE("organize");
			
			public String value;
			
			private SCORE_OPTION(String value){
				this.value = value;
			}
		}
		
	}
}
