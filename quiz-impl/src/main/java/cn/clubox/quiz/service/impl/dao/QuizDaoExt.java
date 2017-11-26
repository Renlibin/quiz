package cn.clubox.quiz.service.impl.dao;

import static cn.clubox.quiz.jooq.domain.tables.Quiz.QUIZ_;
import static cn.clubox.quiz.jooq.domain.tables.QuizPricing.QUIZ_PRICING;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jooq.DSLContext;
import org.jooq.JoinType;
import org.jooq.SelectQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.quiz.jooq.domain.tables.daos.QuizDao;
import cn.clubox.quiz.service.api.model.Quiz.QUIZ_TYPE;

@Repository ("quizDao")
public class QuizDaoExt extends QuizDao{
	
	private static final Logger logger = LoggerFactory.getLogger(QuizDaoExt.class);

	@Autowired
	private DSLContext context;
	
	@PostConstruct
	public void config(){
		super.setConfiguration(context.configuration());
	}
	
	public List<QuizExt> fetchAll(){
		
		logger.info("Fetching all quizs from DB");
		
		SelectQuery<?> query = context.selectQuery();
		query.addSelect(QUIZ_.ID,QUIZ_.NAME,QUIZ_.TITLE,QUIZ_.DESCRIPTION,
				QUIZ_.QUIZ_TYPE,QUIZ_.LOGO_SRC,QUIZ_PRICING.PRICE,QUIZ_PRICING.ORIGINAL_PRICE);
		query.addFrom(QUIZ_);
		query.addJoin(QUIZ_PRICING,JoinType.LEFT_OUTER_JOIN,QUIZ_.ID.equal(QUIZ_PRICING.QUIZ_ID));
		query.addConditions(QUIZ_.STATUS.eq("Y"));
		List<QuizExt> quizExtList = query.fetchInto(QuizExt.class);
		
		return quizExtList;
	}
	
	public QuizExt fetchingQuizByType(String quizType){
		
		return context.selectFrom(QUIZ_).where(QUIZ_.QUIZ_TYPE.eq(quizType))
			.fetchOneInto(QuizExt.class);
	}
	
	public static class QuizExt {
		
		private Integer   id;
	    private String    title;
	    private String    description;
	    private String    status;
	    private Timestamp stored;
	    private String    quizType;
	    private String    logoSrc;
	    private String    name;
	    private BigDecimal price;
	    private BigDecimal originalPrice;
	    
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public Timestamp getStored() {
			return stored;
		}
		public void setStored(Timestamp stored) {
			this.stored = stored;
		}
		public String getQuizType() {
			return quizType;
		}
		public void setQuizType(String quizType) {
			this.quizType = quizType;
		}
		public String getLogoSrc() {
			return logoSrc;
		}
		public void setLogoSrc(String logoSrc) {
			this.logoSrc = logoSrc;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public BigDecimal getPrice() {
			return price;
		}
		public void setPrice(BigDecimal price) {
			this.price = price;
		}
		public BigDecimal getOriginalPrice() {
			return originalPrice;
		}
		public void setOriginalPrice(BigDecimal originalPrice) {
			this.originalPrice = originalPrice;
		}
	}
}
