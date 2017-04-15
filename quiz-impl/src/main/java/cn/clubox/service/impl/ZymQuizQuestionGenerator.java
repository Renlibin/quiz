package cn.clubox.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.clubox.jooq.domain.tables.records.QuizQuestionRecord;
import cn.clubox.quiz.service.api.QuizQuestionGenerator;
import cn.clubox.quiz.service.api.model.Option;
import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;
import cn.clubox.service.impl.dao.QuizQuestionDaoExt;

@Service
public class ZymQuizQuestionGenerator implements QuizQuestionGenerator{
	
	private static final Logger logger = LoggerFactory.getLogger(ZymQuizQuestionGenerator.class);

	@Autowired
	private QuizQuestionDaoExt quizQuestionDao;
	
	@Override
	public QuizQuestion generate() {
		
		logger.info("Generating quiz ZYM's questions");
		
		int quizId = 0;
		List<QuizQuestionRecord> results = quizQuestionDao.fetchQuizQuestionByQuizType(QUIZ_TYPE.zym.value);
		List<Question> questionList = new ArrayList<Question>();
		List<Option> options = this.generateOption();
		
		for(QuizQuestionRecord record : results){
			
			//Initializing QUIZ ID
			if(quizId == 0){
				quizId = record.getQuizId();
			}
			
			Question question = new Question.Builder().id(record.getId()).title(record.getTitle()).optionList(options).build();
//			question.setId(record.getId());
//			question.setTitle(record.getTitle());
//			question.setOptionList(options);
			questionList.add(question);
		}
		
		QuizQuestion quizQuestion = new QuizQuestion(quizId,QUIZ_TYPE.zym.value, questionList);
		return quizQuestion;
	}
	
	private List<Option> generateOption(){
		
		List<Option> options = new ArrayList<Option>();
		
		short orderNumber = 1;
		for(OPTION_DEF optionDef : OPTION_DEF.values()){
			Option option = new Option();
			option.setOptionOrderNumber(orderNumber++);
			option.setOptionText(optionDef.toString());
			option.setOptionValue(optionDef.getValue());
			
			options.add(option);
		}
		return options;
	}
	
	private enum OPTION_DEF {
		//1．从不　2．偶尔　3．有时　4．经常　5．频繁　6．总是
	    NEVER(1){
			@Override
			public String toString(){
				return "从不";
			}
		},
	    OCCASIONALLY(2){
			@Override
			public String toString(){
				return "偶尔";
			}
	    },
	    SOMETIMES(3){
	    	@Override
	    	public String toString(){
	    		return "有时";
	    	}
	    },
	    OFTEN(4){
	    	@Override
	    	public String toString(){
	    		return "经常";
	    	}
	    },
	    FREQUENTLY(5){
	    	@Override
	    	public String toString(){
	    		return "频繁";
	    	}
	    },
	    ALWAYS(6){
	    	@Override
	    	public String toString(){
	    		return "总是";
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
