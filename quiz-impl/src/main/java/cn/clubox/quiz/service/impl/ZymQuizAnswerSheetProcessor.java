package cn.clubox.quiz.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

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
import cn.clubox.quiz.service.impl.ZymQuizAnswerSheetProcessor.ZymScore.SCORE_OPTION;
import cn.clubox.quiz.service.impl.dao.QuizEngagementDaoExt;
import cn.clubox.quiz.service.impl.dao.QuizEngagementResultDaoExt;

@Service
public class ZymQuizAnswerSheetProcessor implements QuizAnswerSheetProcessor {

	private static final Logger logger = LoggerFactory.getLogger(ZymQuizAnswerSheetProcessor.class);
	
	@Autowired
	private QuizEngagementDaoExt quizEngagementDao;
	
	@Autowired
	private QuizEngagementResultDaoExt quizEngagementResultDao;
	
	private List<ScoringRule> scoringRuleList = new ArrayList<ScoringRule>();
	
	@PostConstruct
	public void init(){
		
		logger.info("Initializing Score Rules");
		
		scoringRuleList.add(new ScoringRule() {
			
			private Short[] questions = {1,9,17,25,33};
			private Set<Short> qs = new HashSet<Short>(Arrays.asList(questions));
			@Override
			public boolean scoring(ZymScore score, Question question) {
				
				/**
				 * Should use dedicated question id. One more column should be add in DB
				 */
				if(qs.contains(question.getSequenceNumber())){
					score.setTfScore((short)(score.getTfScore() + question.getSelectedOptionKey()));
					return true;
				}
				return false;
			}
		});
		
		scoringRuleList.add(new ScoringRule(){

			private Short[] questions = {2,10,18,26,34};
			private Set<Short> qs = new HashSet<Short>(Arrays.asList(questions));
			@Override
			public boolean scoring(ZymScore score, Question question) {

				/**
				 * Should use dedicated question id. One more column should be add in DB
				 */
				if(qs.contains(question.getSequenceNumber())){
					score.setGmScore((short)(score.getGmScore() + question.getSelectedOptionKey()));
					return true;
				}
				return false;
			}
		});
		
		scoringRuleList.add(new ScoringRule(){

			private Short[] questions = {3,11,19,27,35};
			private Set<Short> qs = new HashSet<Short>(Arrays.asList(questions));
			@Override
			public boolean scoring(ZymScore score, Question question) {
				
				if(qs.contains(question.getSequenceNumber())){
					score.setAuScore((short)(score.getAuScore() +question.getSelectedOptionKey()));
					return true;
				}
				return false;
			}
			
		});
		
		scoringRuleList.add(new ScoringRule(){
			
			private Short[] questions = {4,12,20,28,36};
			private Set<Short> qs = new HashSet<Short>(Arrays.asList(questions));

			@Override
			public boolean scoring(ZymScore score, Question question) {
				
				if(qs.contains(question.getSequenceNumber())){
					score.setSeScore((short)(score.getSeScore() + question.getSelectedOptionKey()));
					return true;
				}
				return false;
			}
			
		});
		
		scoringRuleList.add(new ScoringRule(){

			private Short[] questions = {5,13,21,29,37};
			private Set<Short> qs = new HashSet<Short>(Arrays.asList(questions));
			
			@Override
			public boolean scoring(ZymScore score, Question question) {
				
				if(qs.contains(question.getSequenceNumber())){
					score.setEcScore((short)(score.getEcScore() + question.getSelectedOptionKey()));
				}
				return false;
			}
			
		});
		
		scoringRuleList.add(new ScoringRule(){

			private Short[] questions = {6,14,22,30,38};
			private Set<Short> qs = new HashSet<Short>(Arrays.asList(questions));
			
			@Override
			public boolean scoring(ZymScore score, Question question) {

				if(qs.contains(question.getSequenceNumber())){
					score.setSvScore((short)(score.getSvScore() + question.getSelectedOptionKey()));
					return true;
				}
				return false;
			}
		});
		
		scoringRuleList.add(new ScoringRule(){

			private Short[] questions = {7,15,23,31,39};
			private Set<Short> qs = new HashSet<Short>(Arrays.asList(questions));
			@Override
			public boolean scoring(ZymScore score, Question question) {
				
				if(qs.contains(question.getSequenceNumber())){
					score.setChScore((short)(score.getChScore() + question.getSelectedOptionKey()));
					return true;
				}
				return false;
			}
		});
		
		scoringRuleList.add(new ScoringRule(){

			private Short[] questions = {8,16,24,32,40};
			private Set<Short> qs = new HashSet<Short>(Arrays.asList(questions));
			@Override
			public boolean scoring(ZymScore score, Question question) {
				
				if(qs.contains(question.getSequenceNumber())){
					score.setLsScore((short)(score.getLsScore() + question.getSelectedOptionKey()));
					return true;
				}
				return false;
			}
		});
	}
	
	@Override
	public int process(QuizAnswerSheet quizAnswerSheet) {	
		
		if(logger.isDebugEnabled()){
			logger.debug("Start to process ZYM quiz answer sheet");
		}
		
		ZymScore zymScore = new ZymScore();
		for(Question question : quizAnswerSheet.getQuestionList()){
			
			//Question id will be used to retrieve score of the question according to the selected option
			for(ScoringRule scoringRule : scoringRuleList){
				//The loop will be stopped if the question processed by one of the Scoring Rules
				if(scoringRule.scoring(zymScore, question)){
					break;
				}
			}
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("The final score is {}", zymScore.toString());
		}
		
		int quizEngagementId = this.persistQuizEngagement(quizAnswerSheet);
		this.persistQuizEngagementResult(quizEngagementId, zymScore);
		
		return quizEngagementId;
		
	}

	@Override
	public String getQuizName() {
		
		return QUIZ_TYPE.ZYM.value;
	}
	
	/****** private methods ******/
	
	private int persistQuizEngagement(QuizAnswerSheet quizAnswerSheet){
		
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
	
	private void persistQuizEngagementResult(int quizEngagementId, ZymScore zymScore){
		
		List<QuizEngagementResult>quizEngagementResultList = new ArrayList<>();
		
		for(SCORE_OPTION sopt : ZymScore.SCORE_OPTION.values()){
			
			short value = 0;
			String resultOption = sopt.value;
			
			//TO generate get method 
			String methodName = "get".concat(resultOption.substring(0, 1).toUpperCase().concat(resultOption.substring(1)).concat("Score"));
			
			try {
				Method method = zymScore.getClass().getMethod(methodName);
				
				if(logger.isDebugEnabled()){
					logger.debug("The method {} is going to be invoked", methodName);
				}
				
				value = (short)method.invoke(zymScore);
				
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
	
	/****** Interface ******/
	
	public interface ScoringRule {
		boolean scoring(ZymScore score, Question question);
	}
	
	/****** Inner class ******/
	
	public static class ZymScore {
		
		private short tfScore;
		private short gmScore;
		private short auScore;
		private short seScore;
		private short ecScore;
		private short svScore;
		private short chScore;
		private short lsScore;
		
		public short getTfScore() {
			return tfScore;
		}
		public void setTfScore(short tfScore) {
			this.tfScore = tfScore;
		}
		public short getGmScore() {
			return gmScore;
		}
		public void setGmScore(short gmScore) {
			this.gmScore = gmScore;
		}
		public short getAuScore() {
			return auScore;
		}
		public void setAuScore(short auScore) {
			this.auScore = auScore;
		}
		public short getSeScore() {
			return seScore;
		}
		public void setSeScore(short seScore) {
			this.seScore = seScore;
		}
		public short getEcScore() {
			return ecScore;
		}
		public void setEcScore(short ecScore) {
			this.ecScore = ecScore;
		}
		public short getSvScore() {
			return svScore;
		}
		public void setSvScore(short svScore) {
			this.svScore = svScore;
		}
		public short getChScore() {
			return chScore;
		}
		public void setChScore(short chScore) {
			this.chScore = chScore;
		}
		public short getLsScore() {
			return lsScore;
		}
		public void setLsScore(short lsScore) {
			this.lsScore = lsScore;
		}
		
		public static enum SCORE_OPTION {
			
			TF("tf"),GM("gm"),AU("au"),SE("se"),EC("ec"),SV("sv"),CH("ch"),LS("ls");
			
			public String value;
			
			private SCORE_OPTION(String value){
				this.value = value;
			}
		}
		@Override
		public String toString() {
			return "ZymScore [tfScore=" + tfScore + ", gmScore=" + gmScore + ", auScore=" + auScore + ", seScore="
					+ seScore + ", ecScore=" + ecScore + ", svScore=" + svScore + ", chScore=" + chScore + ", lsScore="
					+ lsScore + "]";
		}
	}
}