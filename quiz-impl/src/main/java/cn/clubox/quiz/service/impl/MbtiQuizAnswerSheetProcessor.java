package cn.clubox.quiz.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.Quiz.QuizType;

@Service
public class MbtiQuizAnswerSheetProcessor extends AbstractQuizAnswerSheetProcessor {
	
	private static Logger logger = LoggerFactory.getLogger(MbtiQuizAnswerSheetProcessor.class);

	@Override
	public String getQuizName() {
		return QuizType.MBTI.value;
	}
	
	/**
	 * 
	 *  a) 60道题，分别考察4个维度， 8个结果（E，I；S，N；T，F；J，P），每个维度15道题目，每个题目分别考察1个维度的2个结果				
	 *	   "维度一：驱动力的来源：外向E - 内向I
	 *		维度二：接受信息的方式：感觉S - 直觉N
	 *  	维度三：决策的方式：思维T - 情感F
	 *		维度四：如何规划自己的生活：判断J - 知觉P"			
 *		b) 每个题目的答案被选中时，对应的结果加1分；每个维度的总分都是15分，例如外向E=10分，则内向I=5分。				
	 *	c) 维度各单项百分比计算方法：“单项高的得分/15×100%”后取整，例如外向10分，内向5分，则取整（10/15×100%）=66%，报告中显示：驱动力的来源：外向E 66%。				
	 */

	@Override
	public String getResultOption(Question question) {
		
		if(logger.isDebugEnabled()){
			logger.debug("Selected option value is {}", question.getSelectedOptionKey());
		}
		
		for(RESULT_DIMENSION rd : RESULT_DIMENSION.values()){
			if(question.getSelectedOptionKey() == rd.value){
				return rd.toString();
			}
		}
		return null;
	}
	
    public enum RESULT_DIMENSION{
		
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
		
		E(69){
			@Override
			public String toString(){
				return "E";
			}
		},I(73){
			@Override
			public String toString(){
				return "I";
			}
		},S(83){
			@Override
			public String toString(){
				return "S";
			}
		},N(78){
			@Override
			public String toString(){
				return "N";
			}
		},T(84){
			@Override
			public String toString(){
				return "T";
			}
		},F(70){
			@Override
			public String toString(){
				return "F";
			}
		},J(74){
			@Override
			public String toString(){
				return "J";
			}
		},P(80){
			@Override
			public String toString(){
				return "P";
			}
		};
		
		int value;
		
		RESULT_DIMENSION(int value){
			this.value = value;
		}
		
	}

}
