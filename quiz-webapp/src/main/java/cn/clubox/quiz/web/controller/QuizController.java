package cn.clubox.quiz.web.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.clubox.quiz.service.api.QuizAnswerSheetProcessor;
import cn.clubox.quiz.service.api.QuizManager;
import cn.clubox.quiz.service.api.QuizResultGenerator;
import cn.clubox.quiz.service.api.model.Question;
import cn.clubox.quiz.service.api.model.Quiz.QuizType;
import cn.clubox.quiz.service.api.model.QuizAnswerSheet;
import cn.clubox.quiz.service.api.model.QuizExtension;
import cn.clubox.quiz.service.api.util.PagedListHolder;
import cn.clubox.quiz.service.api.util.PagedModel;
import cn.clubox.quiz.service.impl.auth.DatabaseUserDetailsService.User;
import cn.clubox.quiz.web.utils.QuizAnswerSheetProcessorFactory;

@Controller
public class QuizController {

	private static final Logger logger = LoggerFactory.getLogger(QuizController.class);
	
	@Autowired
	private QuizManager quizManager;
	
	@Autowired
	private List<QuizResultGenerator> quizResultGenerators;
	
	@Autowired
	private QuizAnswerSheetProcessorFactory quizAnswerSheetProcessorFactory;
	
	@GetMapping("quiz/refresh")
	public String refreshQuiz(){
		
		logger.info("Refreshing quizs and questions ...... ");
		return "redirect:/quiz/home";
	}
	
	@GetMapping("quiz/index")
	public String showAllQuiz(@AuthenticationPrincipal User user, Map<String, Object> model){
		
		int userId = user.getId();
		List<QuizExtension> quizExtensionList = quizManager.retrieveAllQuiz(userId, true, false, null);
		
		model.put("quizExtensionList", quizExtensionList);
		return "index";
	}
	
	@GetMapping("quiz/{quizType}/engagement")
	public String showQuiz(@AuthenticationPrincipal User user, @PathVariable String quizType,
			@RequestParam(value="engagementId", required=false) Integer engagementId,
			@RequestParam(value="page", required=false) Integer page,
			@RequestParam(value="pageSize", required=false) Integer pageSize, Map<String, Object> model){
		
		int userId = user.getId();
		
		if(this.verifyQuizType(quizType) == false){
			logger.error("The quiz type {} is not exist", quizType);
			return "404";
		}
		
		//Need to check whether the user has payed for the quiz
		if(quizManager.hasPrivilige(userId,quizType) == false){
			"redirect:/quiz".concat(quizType).concat("/buynow");
		}
		
		QuizExtension quizExtension = quizManager.retrieveQuizByType(userId, false, true, null, quizType);
		//To retrieve paged questions according to page number and page size
		PagedModel<? extends Question> pagedQuestionModel = quizManager.retrievePagedQuestionModel(
				quizType,engagementId,page == null ? 1 : page, pageSize == null ? PagedListHolder.DEFAULT_PAGE_SIZE : pageSize);
		
		PagedListHolder<? extends Question> pagedQuestionHolder = new PagedListHolder<>(pagedQuestionModel.getSource());
		pagedQuestionHolder.setPage(pagedQuestionModel.getPage());
		pagedQuestionHolder.setPageSize(pageSize != null ? pageSize : PagedListHolder.DEFAULT_PAGE_SIZE);
		pagedQuestionHolder.setNrOfElements(pagedQuestionModel.getNrOfElements());
		quizExtension.getQuiz().setPagedListHolder(pagedQuestionHolder);
		
		if(logger.isDebugEnabled()){
			logger.debug("Is the last page {}",pagedQuestionHolder.isLastPage());
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("Paged question size is {}", quizExtension.getQuiz().getPagedListHolder().getSource().size());
		}
		
		model.put("engagementId", engagementId);
		model.put("quizExtension", quizExtension);
		return "quiz_engagement";
	}
	
	@PostMapping("quiz/{quizType}/engagement")
	public String submitQuiz(@AuthenticationPrincipal User user, @PathVariable String quizType, 
			@ModelAttribute QuizAnswerSheet quizAnswerSheet, Map<String, Object> model){
		
		int userId = user.getId();
		quizAnswerSheet.setUserId(userId);
		
		if(this.verifyQuizType(quizType) == false){
			logger.error("The quiz type {} is not exist", quizType);
			return "404";
		}
		
		//Need to check whether the user has payed for the quiz
		if(quizManager.hasPrivilige(userId,quizType) == false){
			"redirect:/quiz".concat(quizType).concat("/buynow");
		}
		
		QuizAnswerSheetProcessor<?> processor = quizAnswerSheetProcessorFactory.getProcessor(quizType);
		
		logger.info("Processor is {}", processor);
		
		if(processor == null){
			return "error";
		}
		
		int engagementId = processor.process(quizAnswerSheet);
		
		if(quizAnswerSheet.isLastPage() == false){
			return "redirect:/quiz/".concat(quizType).concat("/engagement?engagementId=")
					.concat(String.valueOf(engagementId).concat("&page=").concat(String.valueOf(quizAnswerSheet.getPage() + 1))
							.concat("&pageSize=").concat(String.valueOf(quizAnswerSheet.getPageSize())));
		}
		
		return "redirect:/quiz/".concat(quizType).concat("/result?engagementId=").concat(String.valueOf(engagementId));
	}
	
//	@GetMapping("quiz/{quizType}/buynow")
//	public String buyNow(@AuthenticationPrincipal User user, @PathVariable String quizType, Map<String, Object> model){
//		
//		logger.info("User {} is going to buy quiz {}", user.getId(), quizType);
//		
//		if(this.verifyQuizType(quizType) == false){
//			logger.error("The quiz type {} is not exist", quizType);
//			return "404";
//		}
//		
//		QuizExtension targetQuizExtension = quizManager.retrieveQuizByType(user.getId(), true, true,QUIZ_DOABLE_ACTION.PAYNOW, quizType);
//		List<QuizExtension> quizExtensionList = quizManager.retrieveAllQuiz(user.getId(),true, false, null);
//		
//		//To exclusive the target quiz
//		for(int i=0; i < quizExtensionList.size(); i++){
//			if(quizExtensionList.get(i).getQuiz().getId() == targetQuizExtension.getQuiz().getId()){
//				quizExtensionList.remove(i);
//			}
//		}
//		
//		model.put("quizExtension", targetQuizExtension);
//		model.put("quizExtensionList",quizExtensionList);
//		
//		return "buynow";
//	}
//	
//	@GetMapping("quiz/{quizType}/paynow")
//	public String payNow(@AuthenticationPrincipal User user, @PathVariable String quizType, Map<String,Object> model){
//		
//		if(this.verifyQuizType(quizType) == false){
//			logger.error("The quiz type {} is not exist", quizType);
//			return "404";
//		}
//		QuizExtension quizExtension = quizManager.retrieveQuizByType(user.getId(), true, false, QUIZ_DOABLE_ACTION.PAYMENT, quizType);
//		
//		model.put("quizExtension", quizExtension);
//		return "paynow";
//	}
	
	@GetMapping("quiz/{quizType}/result")
	public String showResult(@AuthenticationPrincipal User user, @PathVariable String quizType, 
			@RequestParam(value="engagementId",required=false) Integer engagementId, Map<String, Object> model){
		
		Map<String,Short> resultMap = new LinkedHashMap<>();
		
		for(QuizResultGenerator generator : quizResultGenerators){
			resultMap = generator.retrieveQuizEngagementResult(engagementId, user.getId(), quizType);
			if(resultMap != null && resultMap.isEmpty() == false){
				break;
			}
		}
		
		QuizExtension quizExtension = quizManager.retrieveQuizByType(user.getId(), false, true, null, quizType);
		
		model.put("resultMap", resultMap);
		model.put("quizExtension", quizExtension);
		
		return quizType.concat("_result");
	}
	
	@GetMapping("quiz/private/engaged")
	public String engagedQuiz(@AuthenticationPrincipal User user, Map<String,Object> model){
		
		int userId = user.getId();
		String nickname = user.getNickname();
		String portraitSrc = user.getPortraitSrc();
		
		List<QuizExtension> engagedQuizList = quizManager.retrieveEngagedQuiz(userId, true, false);
		
		if(logger.isDebugEnabled()){
			logger.debug("{} engaged {} quizs", nickname, engagedQuizList == null ? 0 :engagedQuizList.size());
		}
		
		model.put("nickname",nickname);
		model.put("portraitSrc",portraitSrc);
		model.put("engagedQuizList", engagedQuizList);
		return "private_quiz_engaged";
	}
	
	@GetMapping("quiz/private/undone")
	public String undoneQuiz(@AuthenticationPrincipal User user, Map<String,Object> model){
		
		int userId = user.getId();
		String nickname = user.getNickname();
		String portraitSrc = user.getPortraitSrc();
		List<QuizExtension> undoneQuizList = quizManager.retrieveUndoneQuiz(userId, true, false);
		
		if(logger.isDebugEnabled()){
			logger.debug("undoneQuizList size is {}", undoneQuizList.size());
		}
		
		model.put("nickname",nickname);
		model.put("portraitSrc",portraitSrc);
		model.put("undoneQuizList", undoneQuizList);
		return "private_quiz_undone";
	}
	
	private boolean verifyQuizType(String quizType){
		if(QuizType.getByValue(quizType) != null){
			return true;
		}
		return false;
	}
}
