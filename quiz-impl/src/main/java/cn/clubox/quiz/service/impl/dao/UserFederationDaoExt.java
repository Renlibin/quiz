package cn.clubox.quiz.service.impl.dao;

import javax.annotation.PostConstruct;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.quiz.jooq.domain.tables.daos.UserFederationDao;

@Repository
public class UserFederationDaoExt extends UserFederationDao {

	private static final Logger logger = LoggerFactory.getLogger(UserFederationDaoExt.class);
	
	@Autowired
	private DSLContext context;
	
	@PostConstruct
	public void config(){
		super.setConfiguration(context.configuration());
	}
	
}
