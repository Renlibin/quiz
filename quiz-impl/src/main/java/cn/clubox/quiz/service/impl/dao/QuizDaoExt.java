package cn.clubox.quiz.service.impl.dao;

import static cn.clubox.quiz.jooq.domain.tables.Quiz.QUIZ_;
import static cn.clubox.quiz.jooq.domain.tables.QuizPricing.QUIZ_PRICING;
import static cn.clubox.quiz.jooq.domain.tables.UserPayment.USER_PAYMENT;

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
import cn.clubox.quiz.service.api.util.Status;

@Repository ("quizDao")
public class QuizDaoExt extends QuizDao{
	
	private static final Logger logger = LoggerFactory.getLogger(QuizDaoExt.class);
	
	private static final String EXTERNAL_QUIZ = "external_quiz";

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
		query.addConditions(QUIZ_.STATUS.equal(Status.NORMAL.getValue()));
		query.addConditions(QUIZ_.QUIZ_TYPE.notEqual(EXTERNAL_QUIZ));
		List<QuizExt> quizExtList = query.fetchInto(QuizExt.class);
		
		return quizExtList;
	}
	
	public QuizExt fetchingQuizByType(String quizType){
		
		return context.select(QUIZ_.ID,QUIZ_.NAME,QUIZ_.TITLE,QUIZ_.DESCRIPTION,QUIZ_.QUIZ_TYPE,QUIZ_.LOGO_SRC,
				QUIZ_PRICING.PRICE,QUIZ_PRICING.ORIGINAL_PRICE).
				from(QUIZ_.innerJoin(QUIZ_PRICING).on(QUIZ_.ID.equal(QUIZ_PRICING.QUIZ_ID)))
				.where(QUIZ_.QUIZ_TYPE.equal(quizType).and(QUIZ_.STATUS.equal(Status.NORMAL.getValue())))
			.fetchOneInto(QuizExt.class);
	}
	
	public QuizExt fetchingQuizBySrc(String quizSrc){
		
		return context.select(QUIZ_.ID,QUIZ_.NAME,QUIZ_.TITLE,QUIZ_.DESCRIPTION,QUIZ_.QUIZ_SRC,QUIZ_.LOGO_SRC,
				QUIZ_PRICING.PRICE,QUIZ_PRICING.ORIGINAL_PRICE).from(QUIZ_.innerJoin(QUIZ_PRICING).on(QUIZ_.ID.equal(QUIZ_PRICING.QUIZ_ID)))
				.where(QUIZ_.QUIZ_SRC.equal(quizSrc).and(QUIZ_.STATUS.equal(Status.NORMAL.getValue())))
		    .fetchOneInto(QuizExt.class);
	}
	
	public List<QuizExt> fetchingPaidExternalQuizByUserId(Integer userId){
		
		return context.select(QUIZ_.ID,QUIZ_.NAME,QUIZ_.TITLE,QUIZ_.DESCRIPTION,QUIZ_.QUIZ_SRC,QUIZ_.LOGO_SRC)
			.from(QUIZ_.innerJoin(USER_PAYMENT).on(QUIZ_.ID.equal(USER_PAYMENT.QUIZ_ID)))
			.where(USER_PAYMENT.USER_ID.equal(userId).and(USER_PAYMENT.STATUS.equal(Status.COMPLETE.getValue()))
					.and(QUIZ_.QUIZ_TYPE.equal("external")))
			.orderBy(USER_PAYMENT.STORED.asc()).fetchInto(QuizExt.class);
			
	}
	
	public static class QuizExt {
		
		private Integer   id;
	    private String    title;
	    private String    description;
	    private String    status;
	    private Timestamp stored;
	    private String    quizType;
	    private String    logoSrc;
	    private String    quizSrc;
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
		public String getQuizSrc() {
			return quizSrc;
		}
		public void setQuizSrc(String quizSrc) {
			this.quizSrc = quizSrc;
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
		@Override
		public String toString() {
			return "QuizExt [id=" + id + ", title=" + title + ", description=" + description + ", status=" + status
					+ ", stored=" + stored + ", quizType=" + quizType + ", logoSrc=" + logoSrc + ", quizSrc=" + quizSrc
					+ ", name=" + name + ", price=" + price + ", originalPrice=" + originalPrice + "]";
		}
	}
}
