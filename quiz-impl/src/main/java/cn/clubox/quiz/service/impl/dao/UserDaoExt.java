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
		
		cn.clubox.quiz.jooq.domain.tables.User userTable = cn.clubox.quiz.jooq.domain.tables.User.USER;
		
		Record record =context.insertInto(userTable, userTable.NAME, userTable.NICKNAME, 
				userTable.PASSWORD, userTable.PORTRAIT_SRC, userTable.STATUS,
				userTable.STORED, userTable.UPDATED).values(user.getName(),user.getNickname()
				,user.getPassword(), user.getPortraitSrc(),user.getStatus(),
				new Timestamp(new Date().getTime()), new Timestamp(new Date().getTime()))
				.returning(userTable.ID).fetchOne();
		
		return record.getValue(userTable.ID);
	}
}
