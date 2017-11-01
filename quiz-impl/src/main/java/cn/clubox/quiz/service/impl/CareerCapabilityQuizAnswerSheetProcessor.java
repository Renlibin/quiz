package cn.clubox.quiz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;
import cn.clubox.quiz.service.impl.CareerCapabilityQuizAnswerSheetProcessor.CareerCapabilityScore.SCORE_OPTION;
import cn.clubox.quiz.service.impl.dao.QuizEngagementDaoExt;
import cn.clubox.quiz.service.impl.dao.QuizEngagementResultDaoExt;

@Service
public class CareerCapabilityQuizAnswerSheetProcessor extends AbstractQuizAnswerSheetProcessor {

	private static final Logger logger = LoggerFactory.getLogger(CareerCapabilityQuizAnswerSheetProcessor.class);
	
	@Override
	public String getQuizName() {
		return QUIZ_TYPE.CAREERCAPABILITY.value;
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
	
	@Override
	public String getResultOption(Question question) {
		
		if(isBetween(question.getSequenceNumber(),1,6)){
			return SCORE_OPTION.LANGUAGE.value;
		}
		else if(isBetween(question.getSequenceNumber(),7,12)){
			return SCORE_OPTION.MATH.value;
		}
		else if(isBetween(question.getSequenceNumber(),13,18)){
			return SCORE_OPTION.JUDGEMENT.value;
		}
		else if(isBetween(question.getSequenceNumber(),19,24)){
			return SCORE_OPTION.DETAIL.value;
		}
		else if(isBetween(question.getSequenceNumber(),25,30)){
			return SCORE_OPTION.WRITING.value;
		}
		else if(isBetween(question.getSequenceNumber(),31,36)){
			return SCORE_OPTION.SPORT.value;
		}
		else if(isBetween(question.getSequenceNumber(),37,42)){
			return SCORE_OPTION.WORK.value;
		}
		else if(isBetween(question.getSequenceNumber(),43,48)){
			return SCORE_OPTION.SOCIAL.value;
		}
		else{
			return SCORE_OPTION.ORGANIZE.value;
		}
	}
}
