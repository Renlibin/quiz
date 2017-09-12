package cn.clubox.quiz.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.jooq.domain.tables.records.QuizQuestionRecord;
import cn.clubox.quiz.service.api.QuizQuestionGenerator;
import cn.clubox.quiz.service.api.model.Option;
import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;
import cn.clubox.quiz.service.impl.dao.QuizQuestionDaoExt;

@Service
public class ConfidenceQuizQuestionGenerator implements QuizQuestionGenerator {

	private static final Logger logger = LoggerFactory.getLogger(ConfidenceQuizQuestionGenerator.class);
	
	@Autowired
	private QuizQuestionDaoExt quizQuestionDao;
	
	@Override
	public QuizQuestion generate() {
		
		logger.info("Generating quiz Confidence's question");
		
		int quizId = 0;
		List<QuizQuestionRecord> results = quizQuestionDao.fetchQuizQuestionByQuizType(QUIZ_TYPE.CONFIDENCE.value);
		List<Question> questionList = new ArrayList<Question>();
		List<Option> options = this.generateOption();
		
		for(QuizQuestionRecord record : results){
			
			//Initializing QUIZ ID
			if(quizId == 0){
				quizId = record.getQuizId();
			}
			
			Question question = new Question.Builder().id(record.getId()).sequenceNumber(record.getSequenceNumber()).title(record.getTitle()).optionList(options).build();
			questionList.add(question);
		}
		
		QuizQuestion quizQuestion = new QuizQuestion(quizId,QUIZ_TYPE.CONFIDENCE.value, questionList);
		return quizQuestion;
	}
	
	private List<Option> generateOption(){
		
		List<Option> options = new ArrayList<Option>();
		
		short orderNumber = 1;
		for(OPTION_DEF optionDef : OPTION_DEF.values()){
			Option option = new Option(String.valueOf(orderNumber++), optionDef.toString(), optionDef.getValue());
			options.add(option);
		}
		return options;
	}
	
	private enum OPTION_DEF {
		
       ABSOLUTELYNOT(1){
    	   @Override   
    	   public String toString(){
    	       return "完全不是";
    	   }
       },
       NOT(2){
    	   @Override   
    	   public String toString(){
    	       return "基本不是";
    	   }
       },
       YES(3){
    	   @Override   
    	   public String toString(){
    	       return "基本是";
    	   }
       },
       ABSOLUTELYYES(4){
    	   @Override   
    	   public String toString(){
    	       return "完全不是";
    	   }
       };
       int value;
		
	   private OPTION_DEF(int val){
		   this.value = val;
	   }
	   
	   public int getValue(){
		   return this.value;
	   }
		
	}

}
