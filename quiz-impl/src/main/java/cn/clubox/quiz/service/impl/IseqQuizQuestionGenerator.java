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
public class IseqQuizQuestionGenerator implements QuizQuestionGenerator{

	private static final Logger logger = LoggerFactory.getLogger(IseqQuizQuestionGenerator.class);
	
	@Autowired
	private QuizQuestionDaoExt quizQuestionDao;
	
	@Override
	public QuizQuestion generate() {

		logger.info("Generating quiz ISEQ's questions");
		
		int quizId = 0;
		List<QuizQuestionRecord> results = quizQuestionDao.fetchQuizQuestionByQuizType(QUIZ_TYPE.ISEQ.value);
		List<Question> questionList = new ArrayList<Question>();
		
		for(QuizQuestionRecord record : results){
			
			//Initializing QUIZ ID
			if(quizId == 0){
				quizId = record.getQuizId();
			}
			
			List<Option> options = this.generateOption(record.getSequenceNumber());
			Question question = new Question.Builder().id(record.getId()).sequenceNumber(record.getSequenceNumber()).title(record.getTitle()).optionList(options).build();
			questionList.add(question);
		}
		
		QuizQuestion quizQuestion = new QuizQuestion(quizId,QUIZ_TYPE.ISEQ.value, questionList);
		return quizQuestion;
	}
	
	private List<Option> generateOption(short questionNumber){
		
		List<Option> options = new ArrayList<Option>(5);
		
		short orderNumber = 65;
		if(questionNumber <= 9){
			for(OPTION_ONE2NINE_DEF optionDef : OPTION_ONE2NINE_DEF.values()){
				Option option = new Option(String.valueOf((char)orderNumber++)
						,optionDef.getDisplayText(questionNumber),optionDef.getValue());
				
				options.add(option);
				
			}
		}else if(questionNumber > 9 && questionNumber  <= 25){
			for(OPTION_TEN2TF_DEF optionDef : OPTION_TEN2TF_DEF.values()){
				Option option = new Option(String.valueOf((char)orderNumber++)
						,optionDef.getDisplayText(questionNumber),optionDef.getValue());
				
				options.add(option);
				
			}
		}else if(questionNumber > 25 && questionNumber <= 29){
			for(OPTION_TS2TN_DEF optionDef : OPTION_TS2TN_DEF.values()){
				Option option = new Option(String.valueOf((char)orderNumber++)
					,optionDef.toString(),optionDef.getValue()	
				);	
				options.add(option);
			}
		}else{
			for(OPTION_TH2TT_DEF optionDef : OPTION_TH2TT_DEF.values()){
				Option option = new Option(String.valueOf((char)orderNumber++)
						,optionDef.toString(),optionDef.getValue()	
					);	
					options.add(option);
			}
		}
		return options;
	}
	
	private enum OPTION_ONE2NINE_DEF {
		//A、是的 B、不一定 C、不是的
		//A、和从前相仿 B、不一定 C、和从前不一样
		ONE(6){
			@Override
			public String getDisplayText(short number){
				if(number == 1 || number == 3 || number == 7 || number == 8 || number == 9){
					return "是的";
				}
				if(number == 2){
					return "和从前相仿";
				}
				if(number == 4){
					return "不是的";
				}
				if(number == 5){
					return "从未如此";
				}
				return "我仍能用心工作";
			}
		},
		TWO(3){
			@Override
			public String getDisplayText(short number){
				if(number == 5){
					return "偶然如此";
				}
				if(number == 6 || number == 9){
					return "介于A、C之间";
				}
				return "不一定";
			}
		},
		THREE(0){
			@Override
			public String getDisplayText(short number){
				if(number == 2){
					return "和从前不一样";
				}
				if(number == 4){
					return "是的";
				}
				if(number == 5){
					return "有时如此";
				}
				if(number == 6){
					return "我不能专心且感到愤怒";
				}
				return "不是的";
			}
		};
		
		int value;
		
		private OPTION_ONE2NINE_DEF (int value){
			this.value = value;
		}
		
		public int getValue(){
			return this.value;
		}
		
		public String getDisplayText(short number){
			return null;
		}
	}
	
	private enum OPTION_TEN2TF_DEF {
		
		ONE(5){
			@Override
			public String getDisplayText(short number){
				if(number == 10 || number == 11){
					return "是的";
				}
				if(number == 12){
					return "极易入睡";
				}
				if(number == 13){
					return "不露声色";
				}
				if(number == 14 || number == 15 || number == 16 ){
					return "不是的";
				}
				if(number == 17){
					return "从来没有";
				}
				if(number == 18 || number == 19){
					return "没有";
				}
				if(number == 20){
					return "从来没有想过";
				}
				return "否";
			}
		},
		TWO(2){
			@Override
			public String getDisplayText(short number){
				if(number == 11 || number == 16){
					return "不太确定";
				}
				if(number == 17){
					return "极易偶尔有过";
				}
				if(number == 18 || number == 19){
					return "记不清";
				}
				if(number == 21){
					return "说不清楚";
				}
				if(number == 22){
					return "不清楚";
				}
				if(number == 23 || number == 24 || number == 25){
					return "偶尔是";
				}
				return "介于A、C之间";
			}
		},
		THREE(0){
			@Override
			public String getDisplayText(short number){
				if(number == 10 || number == 11){
					return "不是的";
				}
				if(number == 12){
					return "不易入睡";
				}
				if(number == 13){
					return "大声抗议，以泄己愤";
				}
				if(number == 14 || number == 15 || number == 16 ){
					return "是的";
				}
				if(number == 17){
					return "这是常有的事";
				}
				if(number == 18 || number == 19){
					return "有";
				}
				if(number == 20){
					return "经常想到";
				}
				return "是";
			}
		};
		
		int value;
		
		private OPTION_TEN2TF_DEF(int value){
			this.value = value;
		}
		
		public int getValue(){
			return this.value;
		}
		
		public String getDisplayText(short number){
			return null;
		}
	}
	
	private enum OPTION_TS2TN_DEF{
		YES(0){
			@Override
			public String toString(){
				return "是";
			}
		},
		NO(5){
			@Override
			public String toString(){
				return "否";
			}
		};
		
		int value;
		
		private OPTION_TS2TN_DEF(int value){
			this.value = value;
		}
		
		public int getValue(){
			return this.value;
		}
	}
	
	private enum OPTION_TH2TT_DEF {
		//1.从不  2.几乎不  3.一半时间  4.大多数时间  5.总是

		NEVER(1){
			@Override
			public String toString(){
				return "从不";
			}
		},HARDLY(2){
			@Override
			public String toString(){
				return "几乎不";
			}
		},HALF(3){
			@Override
			public String toString(){
				return "一半时间";
			}
		},MOST(4){
			@Override
			public String toString(){
				return "大多数时间";
			}
		},ALWAYS(5){
			@Override
			public String toString(){
				return "总是";
			}
		};
		
		int value;
		
		public int getValue(){
			return this.value;
		}
		
		private OPTION_TH2TT_DEF(int value){
			this.value = value;
		}
	}
	
	private static String getCharForNumber(int i) {
	    return i > 0 && i < 27 ? String.valueOf((char)(i + 64)) : null;
	}
	
	public static void main(String[] args){
		
		System.out.println(getCharForNumber(10));
	}
}
