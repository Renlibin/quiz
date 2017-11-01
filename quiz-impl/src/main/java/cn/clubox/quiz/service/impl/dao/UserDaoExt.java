package cn.clubox.quiz.service.impl.dao;

import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.clubox.quiz.jooq.domain.tables.daos.UserDao;
import cn.clubox.quiz.jooq.domain.tables.pojos.User;

import static cn.clubox.quiz.jooq.domain.tables.User.USER;

@Repository
public class UserDaoExt extends UserDao {
	
	private static final Logger logger = LoggerFactory.getLogger(UserDaoExt.class);

	@Autowired
	private DSLContext context;

	@PostConstruct
	public void config() {
		super.setConfiguration(context.configuration());
	}

	public int insertWithReturning(User user){
		
		logger.info("New user {} is going to be persisted");
		
		Record record =context.insertInto(USER, USER.NAME, USER.NICKNAME, 
				USER.PASSWORD, USER.PORTRAIT_SRC, USER.STATUS,
				USER.STORED, USER.UPDATED).values(user.getName(),user.getNickname()
				,user.getPassword(), user.getPortraitSrc(),user.getStatus(),
				new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()))
				.returning(USER.ID).fetchOne();
		
		return record.getValue(USER.ID);
	}
}
