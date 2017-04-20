package cn.clubox.quiz.service.impl.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jooq.DSLContext;
import org.jooq.Operator;
import org.jooq.SelectQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.quiz.jooq.domain.tables.QuizPricing;
import cn.clubox.quiz.jooq.domain.tables.daos.QuizDao;

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
		
		cn.clubox.quiz.jooq.domain.tables.Quiz quizTable = cn.clubox.quiz.jooq.domain.tables.Quiz.QUIZ_;
		
		SelectQuery<?> query = context.selectQuery();
		query.addSelect(quizTable.ID,quizTable.NAME,quizTable.TITLE,quizTable.DESCRIPTION,quizTable.QUIZ_TYPE,quizTable.LOGO_SRC,QuizPricing.QUIZ_PRICING.PRICE);
		query.addFrom(quizTable);
		query.addJoin(QuizPricing.QUIZ_PRICING,quizTable.ID.equal(QuizPricing.QUIZ_PRICING.QUIZ_ID));
		query.addConditions(Operator.AND,quizTable.STATUS.eq("Y"),QuizPricing.QUIZ_PRICING.STATUS.eq("Y"));
		List<QuizExt> quizExtList = query.fetchInto(QuizExt.class);
		
		return quizExtList;
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
	}
}
