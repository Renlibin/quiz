package cn.clubox.quiz.service.impl.dao;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.quiz.jooq.domain.tables.daos.QuizDao;

@Repository ("quizDao")
public class QuizDaoExt extends QuizDao implements InitializingBean{
	
	private static final Logger logger = LoggerFactory.getLogger(QuizDaoExt.class);

	@Autowired
	private BasicDataSource dataSource;

	@Override
	public void afterPropertiesSet() throws Exception {
		
		logger.info(" DataSource is {}", dataSource);
		
		logger.info("DataSource connection pool initial size is {}",dataSource.getInitialSize());
		logger.info("DataSource connection max idle time {}",dataSource.getMaxIdle());
	}
	
	
	
}
