package cn.clubox.quiz.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.jooq.domain.tables.pojos.QuizEngagementResult;
import cn.clubox.quiz.service.api.QuizManager;
import cn.clubox.quiz.service.api.QuizQuestionGenerator;
import cn.clubox.quiz.service.api.QuizQuestionGenerator.QuizQuestion;
import cn.clubox.quiz.service.api.QuizUrlEncodeDecodeService;
import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.Quiz;
import cn.clubox.quiz.service.api.model.Quiz.QuizType;
import cn.clubox.quiz.service.api.model.QuizExtension;
import cn.clubox.quiz.service.api.model.QuizExtension.QUIZ_DOABLE_ACTION;
import cn.clubox.quiz.service.api.util.PagedModel;
import cn.clubox.quiz.service.impl.dao.QuizDaoExt;
import cn.clubox.quiz.service.impl.dao.QuizDaoExt.QuizExt;
import cn.clubox.quiz.service.impl.dao.QuizEngagementDaoExt;
import cn.clubox.quiz.service.impl.dao.QuizEngagementResultDaoExt;
import cn.clubox.quiz.service.impl.dao.UserPaymentDaoExt;

@Service
public class DefaultQuizManager implements QuizManager {
	
	private static final Logger logger = LoggerFactory.getLogger(DefaultQuizManager.class);
	
	private static final String QUIZ_URL_PREFIX = "quiz/";
	
	@Autowired
	private QuizDaoExt quizDao;
	
	@Autowired
	private UserPaymentDaoExt userPaymentDao;
	
	@Autowired
	private QuizEngagementDaoExt quizEngagementDao;
	
	@Autowired
	private QuizEngagementResultDaoExt quizEngagementResultDao;
	
	@Autowired
	private List<QuizQuestionGenerator> quizQuestionGeneratorList;
	
	@Autowired
	private QuizUrlEncodeDecodeService quizUrlEncodeDecodeService;
	
	private Map<String, Quiz> quizMap;
	
	private Map<String, List<Question>> questionMap;
	
	
	
	@PostConstruct
	public void init() {
		questionMap = this.questionInitialization();
		quizMap = this.quizInitialization(questionMap);
	}
	
	@Override
	public Integer retrieveQuizIdBySrc(String quizSrc) {
		
		String decodedQuizSrc = quizUrlEncodeDecodeService.decode(quizSrc);
		
		if(logger.isDebugEnabled()){
			logger.debug("DefaultQuizManager.retrieveQuizIdBySrc -> Original quiz src is {}", quizSrc);
		}
		
		QuizExt quiz = quizDao.fetchingQuizIdBySrc(decodedQuizSrc);
		return quiz != null ? quiz.getId() : null;
	}

	@Override
	public List<Integer> retrieveUndoneQuizId(int userId) {
		
		List<Integer> undoneQuizIdList = userPaymentDao.fetchUndoneQuizId(userId);
		
		return undoneQuizIdList;
	}

	@Override
	public List<Integer> retrieveEngagedQuizId(int userId) {
		
		List<Integer> engagedQuizIdList = quizEngagementDao.fetchAllDistinctLatestEngagedQuizId(userId);
		
		return engagedQuizIdList;
		
	}

	@Override
	public boolean hasPrivilige(int userId, String quizType) {

		List<Integer> paymentIdList = userPaymentDao.fetchUserPaymentByUserIdAndQuizType(userId, quizType);
		
		return (paymentIdList != null && paymentIdList.size() > 0) ? true : false;
	}
	
	@Override
	public List<QuizExtension> retrieveAllQuiz(int userId, boolean setDoableAction, 
				boolean countQuizParticipant, QUIZ_DOABLE_ACTION doableAction) {
		
		List<QuizExtension> quizExtensionList = new ArrayList<QuizExtension>(quizMap.size());
		
		for(Quiz quiz : quizMap.values()){
			QuizExtension quizExtension = new QuizExtension();
			quizExtension.setQuiz(quiz);
			if(countQuizParticipant){
				//To set number of participant
				quizExtension.setNumberOfParticipant(this.countQuizParticipant(quizExtension.getQuiz().getId()));
			}
			
			quizExtensionList.add(quizExtension);
		}
		
		if(setDoableAction){
			//To set doable action of the quiz
			this.doableActionDecision(userId, doableAction, quizExtensionList.toArray(new QuizExtension[quizExtensionList.size()]));
		}
		
		return quizExtensionList;
	}

	@Override
	public QuizExtension retrieveQuizByType(int userId, boolean setDoableAction, 
				boolean countQuizParticipant, QUIZ_DOABLE_ACTION doableAction, String quizType) {
		
		Quiz quiz = quizMap.get(quizType);
		
		QuizExtension quizExtension = new QuizExtension();
		quizExtension.setQuiz(quiz);
		
		if(setDoableAction){
			//To set doable action of the quiz
			this.doableActionDecision(userId, doableAction, quizExtension);
		}
		if(countQuizParticipant){
			//To set number of participant
			quizExtension.setNumberOfParticipant(this.countQuizParticipant(quizExtension.getQuiz().getId()));
		}

		return quizExtension;
	}

	@Override
	public List<QuizExtension> retrieveUndoneQuiz(int userId, boolean setDoableAction, boolean countQuizParticipant) {

		List<Integer> undoneQuizIdList = userPaymentDao.fetchUndoneQuizId(userId);
		
		List<Quiz> quizList = new ArrayList<>(quizMap.values());
		List<QuizExtension> undoneQuizList = new ArrayList<>(undoneQuizIdList.size());
		
		for(Quiz quiz : quizList){
			if(undoneQuizIdList.contains(quiz.getId())){
				QuizExtension quizExtension = new QuizExtension();
				
				quizExtension.setQuiz(quiz);
				if(setDoableAction){
					quizExtension.setDoableActionTitle(QuizExtension.QUIZ_DOABLE_ACTION.ENGAGEMENT);
					quizExtension.setDoableActionLink(this.generateActionLink(QUIZ_DOABLE_ACTION.ENGAGEMENT, quizExtension.getQuiz().getQuizType()));
				}
				if(countQuizParticipant){
					quizExtension.setNumberOfParticipant(this.countQuizParticipant(quiz.getId()));
				}
				undoneQuizList.add(quizExtension);
			}
		}
		
		return undoneQuizList;
	}

	@Override
	public List<QuizExtension> retrieveEngagedQuiz(int userId, boolean setDoableAction, boolean countQuizParticipant) {
		
		List<Integer> engagedQuizIdList = quizEngagementDao.fetchAllDistinctLatestEngagedQuizId(userId);
		
		List<Quiz> quizList = new ArrayList<>(quizMap.values());
		List<QuizExtension> engagedQuizList = new ArrayList<>(engagedQuizIdList.size());
		
		for(Quiz quiz : quizList){
			if(engagedQuizIdList.contains(quiz.getId())){
				QuizExtension quizExtension = new QuizExtension();
				
				quizExtension.setQuiz(quiz);
				if(setDoableAction){
					quizExtension.setDoableActionTitle(QuizExtension.QUIZ_DOABLE_ACTION.SHOWRESULT);
					quizExtension.setDoableActionLink(this.generateActionLink(QUIZ_DOABLE_ACTION.SHOWRESULT, quizExtension.getQuiz().getQuizType()));
				}
				if(countQuizParticipant){
					quizExtension.setNumberOfParticipant(this.countQuizParticipant(quiz.getId()));
				}
				engagedQuizList.add(quizExtension);
			}
			
		}
		return engagedQuizList;
	}
	
	
	@Override
	public List<Quiz> retrievePaidExternalQuiz(Integer userId) {
		
		List<Quiz> quizList = new ArrayList<>();
		
		List<QuizExt> quizs = quizDao.fetchingPaidExternalQuizByUserId(userId);
		
		if(Objects.nonNull(quizs) && quizs.isEmpty() == false){
			for(QuizExt q : quizs){
				Quiz quiz = new Quiz.Builder().setId(q.getId()).setName(q.getName())
						.setTitle(q.getTitle()).setDescription(q.getDescription())
						.setQuizSrc(quizUrlEncodeDecodeService.encode(q.getQuizSrc())).build();
				
				quizList.add(quiz);
			}
		}
		return quizList;
	}
	
	private Map<String,Quiz> quizInitialization(Map<String, List<Question>> questionMap) {
		
		List<QuizExt> quizPojoList =quizDao.fetchAll();
		
		if(logger.isDebugEnabled()){
			logger.debug("There are {} quizs will be initialized" , quizPojoList.size());
		}
		
		Map<String,Quiz> quizMap = new TreeMap<String,Quiz>();
		for(QuizExt quizPojo : quizPojoList){
			QuizType quizType = QuizType.getByValue(quizPojo.getQuizType());
			if(quizType != null){
				Quiz quiz = new Quiz.Builder().setId(quizPojo.getId()).setName(quizPojo.getName())
					.setTitle(quizPojo.getTitle()).setDescription(quizPojo.getDescription())
					.setLogoSrc(quizPojo.getLogoSrc()).setPrice(quizPojo.getPrice() != null ? quizPojo.getPrice().setScale(2, BigDecimal.ROUND_UP) : BigDecimal.ZERO)
					.setOriginalPrice(quizPojo.getOriginalPrice() != null ? quizPojo.getOriginalPrice().setScale(2, BigDecimal.ROUND_UP) : BigDecimal.ZERO)
					.setQuizType(quizPojo.getQuizType()).setQuestionList(questionMap.get(quizType.value)).build();
				
				quizMap.put(quiz.getQuizType(), quiz);
			}
		}
		return quizMap;
	}
	
	private Map<String, List<Question>> questionInitialization() {
		
		Map<String, List<Question>> questionMap = new HashMap<String, List<Question>>();
		
		for(QuizQuestionGenerator generator : quizQuestionGeneratorList){
			QuizQuestion quizQuestion = generator.generate();
			if(quizQuestion != null){
				questionMap.put(quizQuestion.getQuizType(), quizQuestion.getQuestionList());
			}
		}
		return questionMap;
	}
	
	private List<QuizExtension> doableActionDecision(int userId, QUIZ_DOABLE_ACTION doableAction,QuizExtension ... quizExtensions) {
		
		List<QuizExtension> quizExtensionList = new ArrayList<QuizExtension>();
		
		if(doableAction != null){
			if(doableAction.equals(QUIZ_DOABLE_ACTION.PAYMENT)){
				for(QuizExtension quizExtension : quizExtensions){
					quizExtension.setDoableActionTitle(doableAction);
					//The URL should be Wechat payment
					quizExtension.setDoableActionLink("http://weixin.qq.com");
					quizExtensionList.add(quizExtension);
				}
			}else{
				for(QuizExtension quizExtension : quizExtensions){
					quizExtension.setDoableActionTitle(doableAction);
					quizExtension.setDoableActionLink(this.generateActionLink(doableAction,quizExtension.getQuiz().getQuizType()));
					quizExtensionList.add(quizExtension);
				}
			}
			return quizExtensionList;
		}
		
		List<Integer> engagedQuizIdList = quizEngagementDao.fetchEngagedQuizId(userId);
		
		if(logger.isDebugEnabled()){
			logger.debug("Engaged quiz ids are {}", engagedQuizIdList.toString());
		}
		
		List<Integer> payedQuizIdList = userPaymentDao.fetchPayedQuizId(userId);
		
		if(logger.isDebugEnabled()){
			logger.debug("Payed quiz ids are {}", payedQuizIdList.toString());
		}
		
		for(QuizExtension quizExtension : quizExtensions){
			if(engagedQuizIdList.contains(quizExtension.getQuiz().getId())){
				quizExtension.setDoableActionTitle(QUIZ_DOABLE_ACTION.SHOWRESULT);
				quizExtension.setDoableActionLink(this.generateActionLink(QUIZ_DOABLE_ACTION.SHOWRESULT, quizExtension.getQuiz().getQuizType()));
			}else if(quizExtension.getQuiz().getPrice().equals(BigDecimal.ZERO) || payedQuizIdList.contains(quizExtension.getQuiz().getId())){
				quizExtension.setDoableActionTitle(QUIZ_DOABLE_ACTION.ENGAGEMENT);
				quizExtension.setDoableActionLink(this.generateActionLink(QUIZ_DOABLE_ACTION.ENGAGEMENT, quizExtension.getQuiz().getQuizType()));
			}else{
				quizExtension.setDoableActionTitle(QUIZ_DOABLE_ACTION.BUYNOW);
				quizExtension.setDoableActionLink(this.generateActionLink(QUIZ_DOABLE_ACTION.BUYNOW, quizExtension.getQuiz().getQuizType()));
			}
		}

		return quizExtensionList;
	}
	
	private int countQuizParticipant(int quizId) {
		return quizEngagementDao.countTotalParticipant(quizId);
	}
	
	private String generateActionLink(QUIZ_DOABLE_ACTION doableAction, String quizType){
//		return quizType.concat("/").concat(doableAction.value);
		return QUIZ_URL_PREFIX.concat(quizType).concat("/").concat(doableAction.value);
	}

	@Override
	public PagedModel<? extends Question> retrievePagedQuestionModel(String quizType, Integer engagementId, int page, int pageSize) {

		if(logger.isDebugEnabled()){
			logger.debug("Paged question list are going to be retrieved according to page {} and size {}", page, pageSize);
		}
		
		List<Question> questions = questionMap.get(quizType);
		
		if(logger.isDebugEnabled()){
			logger.debug("Original question size is {}", questions.size());
		}
		PagedModel<Question> pageModel = new PagedModel<>();
		pageModel.setNrOfElements(questions.size());
		pageModel.setPage(page);
		
		int endIndex = (page * pageSize) < questions.size() ? (page * pageSize) : questions.size();
		
		if(logger.isDebugEnabled()){
			logger.debug("Paged question end index is {}",endIndex);
		}
		List<Question> pagedQuestions = questions.subList((page -1) * pageSize, endIndex);
		
		if(logger.isDebugEnabled()){
			logger.debug("Engagement id is {}", engagementId);
		}
		if(logger.isDebugEnabled()){
			logger.debug("Paged question size is {}", pagedQuestions.size());
		}
		if(Objects.isNull(engagementId) == false){
			//To retrieve engagement result according to engagement id
			List<QuizEngagementResult> engagementResults = quizEngagementResultDao.fetchQuizEngagementResultByIdAndRang(engagementId, (page-1) * pageSize, endIndex);
			if(engagementResults != null && engagementResults.isEmpty() == false){
				
				pagedQuestions.stream().filter(q -> engagementResults.stream().anyMatch(r -> {
						if(r.getQuestionId() == q.getSequenceNumber()){
							q.setEngagementResultId(r.getId());
							q.setSelectedOptionValue(r.getScore());
							return true;
						}
						return false;
					})).collect(Collectors.toList());
			}
		}
		
		pageModel.setSource(pagedQuestions);
	
		return pageModel;
	}

}
