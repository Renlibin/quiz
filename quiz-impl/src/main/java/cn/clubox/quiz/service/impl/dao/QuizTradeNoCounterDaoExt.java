package cn.clubox.quiz.service.impl.dao;

import static cn.clubox.quiz.jooq.domain.tables.QuizTradeNoCounter.QUIZ_TRADE_NO_COUNTER;

import javax.annotation.PostConstruct;

import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.quiz.jooq.domain.tables.daos.QuizTradeNoCounterDao;

@Repository
public class QuizTradeNoCounterDaoExt extends QuizTradeNoCounterDao {

	private static Logger logger = LoggerFactory.getLogger(QuizTradeNoCounterDaoExt.class);
	
	@Autowired
	private DSLContext context;

	@PostConstruct
	public void config() {
		super.setConfiguration(context.configuration());
	}
	
	public Integer fetchingTradeNo(){
		
		if(logger.isDebugEnabled()){
			logger.debug("Fetching the trade NO");
		}
		
		Integer counter = context.select(QUIZ_TRADE_NO_COUNTER.TRADE_NO).from(QUIZ_TRADE_NO_COUNTER)
			.where(QUIZ_TRADE_NO_COUNTER.ID.eq(1)).fetchOneInto(Integer.class);
		
		if(counter == null){
			logger.error("");
		}

		context.update(QUIZ_TRADE_NO_COUNTER).set(QUIZ_TRADE_NO_COUNTER.TRADE_NO,(counter + 1))
			.where(QUIZ_TRADE_NO_COUNTER.ID.eq(1));
		
		return counter;
	}
}