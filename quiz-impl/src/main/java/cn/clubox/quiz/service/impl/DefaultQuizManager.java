package cn.clubox.quiz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.service.api.QuizManager;
import cn.clubox.quiz.service.api.QuizQuestionGenerator;
import cn.clubox.quiz.service.api.QuizQuestionGenerator.QuizQuestion;
import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.Quiz;
import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;
import cn.clubox.quiz.service.api.model.QuizExtension;
import cn.clubox.quiz.service.api.model.QuizExtension.QUIZ_AVILABLE_ACTION;
import cn.clubox.quiz.service.impl.dao.QuizDaoExt;
import cn.clubox.quiz.service.impl.dao.QuizDaoExt.QuizExt;
import cn.clubox.quiz.service.impl.dao.QuizEngagementDaoExt;
import cn.clubox.quiz.service.impl.dao.UserPaymentDaoExt;

@Service
public class DefaultQuizManager implements QuizManager {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultQuizManager.class);
	
	@Autowired
	private QuizDaoExt quizDao;
	
	@Autowired
	private UserPaymentDaoExt userPaymentDao;
	
	@Autowired
	private QuizEngagementDaoExt quizEngagementDao;
	
	@Autowired
	private List<QuizQuestionGenerator> quizQuestionGeneratorList;
	
	@Override
	public Map<String,Quiz> retrieveAllQuiz() {
		
		List<QuizExt> quizPojoList =quizDao.fetchAll();
		
		Map<String,Quiz> quizMap = new TreeMap<String,Quiz>();
		for(QuizExt quizPojo : quizPojoList){
			QUIZ_TYPE quizType = QUIZ_TYPE.getByValue(quizPojo.getQuizType());
			if(quizType != null){
				Quiz quiz = new Quiz();
				quiz.setId(quizPojo.getId());
				quiz.setName(quizPojo.getName());
				quiz.setTitle(quizPojo.getTitle());
				quiz.setDescription(quizPojo.getDescription());
				quiz.setLogoSrc(quizPojo.getLogoSrc());
				quiz.setPrice(quizPojo.getPrice().setScale(2,BigDecimal.ROUND_UP));
				quiz.setQuizType(quizType.value);
				
				quizMap.put(quiz.getQuizType(), quiz);
			}
		}
		return quizMap;
	}
	
	@Override
	public Map<String, List<Question>> retrieveAllQuizQuestion() {
		
		Map<String, List<Question>> questionMap = new HashMap<String, List<Question>>();
		
		for(QuizQuestionGenerator generator : quizQuestionGeneratorList){
			QuizQuestion quizQuestion = generator.generate();
			if(quizQuestion != null){
				questionMap.put(quizQuestion.getQuizType(), quizQuestion.getQuestionList());
			}
		}
		return questionMap;
	}

	@Override
	public Quiz retrieveQuiz(String username, String quizType) {
		return null;
	}

	@Override
	public List<QuizExtension> avilableActionDecision(int userId,Quiz ... quizs) {
		
		List<Integer> engagedQuizIdList = quizEngagementDao.fetchEngagedQuizId(userId);
		
		if(logger.isDebugEnabled()){
			logger.debug("Engaged quiz ids are {}", engagedQuizIdList.toString());
		}
		
		List<Integer> payedQuizIdList = userPaymentDao.fetchPayedQuizId(userId);
		
		if(logger.isDebugEnabled()){
			logger.debug("Payed quiz ids are {}", payedQuizIdList.toString());
		}
		
		List<QuizExtension> quizExtensionList = new ArrayList<QuizExtension>();
		
		for(Quiz quiz : quizs){
			QuizExtension quizExtension = new QuizExtension();
			quizExtension.setQuiz(quiz);
			if(engagedQuizIdList.contains(quiz.getId())){
				quizExtension.setAvilableActionTitle(QUIZ_AVILABLE_ACTION.SHOWRESULT);
			}else if(payedQuizIdList.contains(quiz.getId())){
				quizExtension.setAvilableActionTitle(QUIZ_AVILABLE_ACTION.ENGAGEMENT);
			}else{
				quizExtension.setAvilableActionTitle(QUIZ_AVILABLE_ACTION.PAYNOW);
			}
			
			quizExtensionList.add(quizExtension);
		}

		return quizExtensionList;
	}

}
