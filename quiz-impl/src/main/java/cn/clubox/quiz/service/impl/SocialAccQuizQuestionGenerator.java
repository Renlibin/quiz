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
import cn.clubox.quiz.service.api.model.Quiz.QuizType;
import cn.clubox.quiz.service.impl.dao.QuizQuestionDaoExt;

@Service
public class SocialAccQuizQuestionGenerator implements QuizQuestionGenerator {

	private static final Logger logger = LoggerFactory.getLogger(SocialAccQuizQuestionGenerator.class);
	
	private static int SECOND_PART_QUESTION_INDEX = 10;
	@Autowired
	private QuizQuestionDaoExt quizQuestionDao;
	
	@Override
	public QuizQuestion generate() {

		logger.info("Generating quiz SocialAcc's question");

		int quizId = 0;
		List<QuizQuestionRecord> results = quizQuestionDao.fetchQuizQuestionByQuizType(QuizType.SOCIALACC.value);
		List<Question> questionList = new ArrayList<Question>();
		List<Option> options = null;
		
		for (QuizQuestionRecord record : results) {

			// Initializing QUIZ ID
			if (quizId == 0) {
				quizId = record.getQuizId();
			}
			if(options == null || record.getSequenceNumber() > SECOND_PART_QUESTION_INDEX){
				options = this.generateOption(record.getSequenceNumber());
			}
			
			Question question = new Question.Builder().id(record.getId()).sequenceNumber(record.getSequenceNumber())
					.title(record.getTitle()).optionList(options).build();
			questionList.add(question);
		}

		QuizQuestion quizQuestion = new QuizQuestion(quizId, QuizType.SOCIALACC.value, questionList);
		return quizQuestion;
	}

	private List<Option> generateOption(short questionNumber) {

		List<Option> options = new ArrayList<Option>();

		short orderNumber = 1;

		if (questionNumber <= SECOND_PART_QUESTION_INDEX) {
			for (OPTION_ONT2TEN_DEF optionDef : OPTION_ONT2TEN_DEF.values()) {
				Option option = new Option(String.valueOf(orderNumber++), optionDef.toString(), optionDef.getValue());
				options.add(option);
			}
		} else {
			for (OPTION_TEN2TWN_DEF optionDef : OPTION_TEN2TWN_DEF.values()) {
				Option option = new Option(String.valueOf(orderNumber++), optionDef.toString(), optionDef.getValue());
				options.add(option);
			}
		}
		return options;
	}

	private enum OPTION_ONT2TEN_DEF {

		YES(-2) {
			@Override
			public String toString() {
				return "是";
			}
		},
		UNCERTAIN(0) {
			@Override
			public String toString() {
				return "不确定";
			}
		},
		NO(2) {
			@Override
			public String toString() {
				return "否";
			}
		};
		int value;

		private OPTION_ONT2TEN_DEF(int val) {
			this.value = val;
		}

		public int getValue() {
			return this.value;
		}
	}

	private enum OPTION_TEN2TWN_DEF {

		YES(2) {
			@Override
			public String toString() {
				return "是";
			}
		},
		UNCERTAIN(0) {
			@Override
			public String toString() {
				return "不确定";
			}
		},
		NO(2) {
			@Override
			public String toString() {
				return "否";
			}
		};
		int value;

		private OPTION_TEN2TWN_DEF(int val) {
			this.value = val;
		}

		public int getValue() {
			return this.value;
		}
	}
}
