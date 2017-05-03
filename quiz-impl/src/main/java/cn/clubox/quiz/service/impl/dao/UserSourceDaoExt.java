package cn.clubox.quiz.service.impl.dao;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.quiz.jooq.domain.tables.daos.UserSourceDao;

@Repository
public class UserSourceDaoExt extends UserSourceDao {

	private static final Logger logger = LoggerFactory.getLogger(UserSourceDaoExt.class);
	
	@Autowired
	private DSLContext context;
	
	public void config(){
		super.setConfiguration(context.configuration());
	}
}
