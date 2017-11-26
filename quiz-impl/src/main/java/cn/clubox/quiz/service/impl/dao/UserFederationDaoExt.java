package cn.clubox.quiz.service.impl.dao;

import javax.annotation.PostConstruct;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.quiz.jooq.domain.tables.daos.UserFederationDao;
import static cn.clubox.quiz.jooq.domain.tables.UserFederation.USER_FEDERATION;;

@Repository
public class UserFederationDaoExt extends UserFederationDao {

	private static final Logger logger = LoggerFactory.getLogger(UserFederationDaoExt.class);
	
	@Autowired
	private DSLContext context;
	
	@PostConstruct
	public void config(){
		super.setConfiguration(context.configuration());
	}
	
	public Integer fetchUserIdByFederationId(String federationId){
		
		if(logger.isDebugEnabled()){
			logger.debug("The user id is going to be retrieved via federation id {}", federationId);
		}
		
		return context.select(USER_FEDERATION.USER_ID).from(USER_FEDERATION)
				.where(USER_FEDERATION.FEDERATION_ID.eq(federationId)).fetchOneInto(Integer.class);
	}
	
}
