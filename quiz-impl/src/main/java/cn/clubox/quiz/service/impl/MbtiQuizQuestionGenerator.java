package cn.clubox.quiz.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.jooq.domain.tables.records.QuizQuestionOptionRecord;
import cn.clubox.quiz.jooq.domain.tables.records.QuizQuestionRecord;
import cn.clubox.quiz.service.api.QuizQuestionGenerator;
import cn.clubox.quiz.service.api.model.Option;
import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;
import cn.clubox.quiz.service.impl.dao.QuizQuestionDaoExt;
import cn.clubox.quiz.service.impl.dao.QuizQuestionOptionDaoExt;

@Service
public class MbtiQuizQuestionGenerator implements QuizQuestionGenerator{
	
	private static final Logger logger = LoggerFactory.getLogger(MbtiQuizQuestionGenerator.class);
	
	@Autowired
	private QuizQuestionOptionDaoExt quizQuestionOptionDao;
	
	@Autowired
	private QuizQuestionDaoExt quizQuestionDao;
	
	private List<QuizQuestionOptionRecord> quizQuestionOptionRecordList = null;

	@Override
	public QuizQuestion generate() {
		
		logger.info("Generating quiz MBTI's questions");
		
		int quizId = 0;
		List<QuizQuestionRecord> results = quizQuestionDao.fetchQuizQuestionByQuizType(QUIZ_TYPE.MBTI.value);
		List<Question> questionList = new ArrayList<Question>();
		
		for(QuizQuestionRecord record : results){
			
			//Initializing QUIZ ID
			if(quizId == 0){
				quizId = record.getQuizId();
				if(quizQuestionOptionRecordList == null){
					logger.info("Question options of quiz {} are going to be retrieved", quizId);
					quizQuestionOptionRecordList = quizQuestionOptionDao.fetchQuizId(quizId);
				}
			}
			
			/*
			 * The question options will be retrieved form DB according to the question id
			 */
			List<Option> options = this.generateOption(record.getId());
			Question question = new Question.Builder().id(record.getId()).sequenceNumber(record.getSequenceNumber()).title(record.getTitle()).optionList(options).build();
			questionList.add(question);
		}
		
		QuizQuestion quizQuestion = new QuizQuestion(quizId,QUIZ_TYPE.MBTI.value, questionList);
		
		return quizQuestion;
	}
	
	private List<Option> generateOption(Integer questionNumber){
		
		if(logger.isDebugEnabled()){
			logger.debug("The options of question {} are going to be retrieved", questionNumber);
		}
		
		List<Option> options = new ArrayList<Option>(2);
		List<QuizQuestionOptionRecord> optionRecords = quizQuestionOptionRecordList.stream().filter(r -> r.getQuizQuestionId().equals(questionNumber)).collect(Collectors.toList());
		
		if(optionRecords != null && optionRecords.isEmpty() == false){
			for(QuizQuestionOptionRecord r : optionRecords){
				Option option = new Option(String.valueOf(r.getSequenceNumber()),r.getTitle(),r.getScore());
				options.add(option);
			}
		}
		
		return options;
	}

}
