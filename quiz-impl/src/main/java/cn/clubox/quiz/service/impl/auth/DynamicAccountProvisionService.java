package cn.clubox.quiz.service.impl.auth;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.jooq.domain.tables.pojos.User;
import cn.clubox.quiz.jooq.domain.tables.pojos.UserFederation;
import cn.clubox.quiz.jooq.domain.tables.pojos.UserSource;
import cn.clubox.quiz.service.api.auth.AccountProvisionService;
import cn.clubox.quiz.service.api.auth.WeChatUserInfo;
import cn.clubox.quiz.service.impl.dao.UserDaoExt;
import cn.clubox.quiz.service.impl.dao.UserFederationDaoExt;
import cn.clubox.quiz.service.impl.dao.UserSourceDaoExt;

@Service
public class DynamicAccountProvisionService implements AccountProvisionService {

	private static final Logger logger = LoggerFactory.getLogger(DynamicAccountProvisionService.class);

	@Autowired
	private UserDaoExt userDao;
	
	@Autowired
	private UserFederationDaoExt userFederationDao;
	
	@Autowired
	private UserSourceDaoExt userSourceDao;
	
	private static String USERNAME_PREFIX = "u-";
	private static short  USERNAME_LENGTH = 12;
	private static short  PASSWORD_LENGTH = 8;
	private static short  RETRY_TIMES = 10;

	@Override
	public Integer provisionAccount(WeChatUserInfo userInfo) {
		
		List<UserFederation> userFederations = userFederationDao.fetchByFederationId(userInfo.getUnionid());
		//The user's id will be returned directly if the user exist
		if(userFederations != null && userFederations.isEmpty() ==false){
			return userFederations.get(0).getUserId();
		}
		
		String username = randomString(USERNAME_LENGTH);
		String password = randomString(PASSWORD_LENGTH);
		username = USERNAME_PREFIX.concat(username);
		
		User user = new User();
		user.setName(username);
		user.setNickname(userInfo.getNickname());
		user.setPassword(password);
		user.setPortraitSrc(userInfo.getHeadimgurl());
		user.setStatus("Y");
		
		logger.info("User {}'s open id is {}", userInfo.getNickname(), userInfo.getOpenid());
		
		Integer userId = 0;
		boolean isSuccess = false;
		while(!isSuccess){
			try{
				userId = userDao.insertWithReturning(user);
				isSuccess = true;
			}catch(Exception e){
				if(RETRY_TIMES <= 0){
					logger.warn("The unique user name could not be generated after {} times retry. User provision failed!",RETRY_TIMES);
					return null;
				}
				logger.warn("The username {} is already exist in DB", user.getName());
				//New username should be regenerated while the username is already exist in DB
				user.setName(USERNAME_PREFIX.concat(randomString(USERNAME_LENGTH)));
				RETRY_TIMES --;
			}
		}
		
		//user_federation and user_source should be persisted as well
		//UserFederation is going to be persisted in DB
		UserFederation userFederation = new UserFederation();
		userFederation.setUserId(userId);
		userFederation.setFederationId(userInfo.getUnionid());
		userFederation.setStatus("Y");
		userFederation.setStored(new Timestamp(new Date().getTime()));
		userFederationDao.insert(userFederation);

		//UserSource is going to be persisted in DB
//		UserSource userSource = new UserSource();
//		userSource.setUserId(userId);
//		userSource.setIpAddress("");
//		userSource.setSource("");
//		userSource.setChannel("");
//		userSourceDao.insert(userSource);
		
		return userId;

	}

	private String randomString(final int length) {

		final String alpha = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		SecureRandom rnd = new SecureRandom();

		StringBuilder sb = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			sb.append(alpha.charAt(rnd.nextInt(alpha.length())));
		}
		
		return sb.toString();
	}

	public static void main(String[] args) {

		// System.out.println(randomString(8));

		DynamicAccountProvisionService pp = new DynamicAccountProvisionService();
//
//		long s = System.currentTimeMillis();
//		System.out.println(pp.randomPassword(6));
//
//		System.out.println("Total " + (System.currentTimeMillis() - s));
//
//		System.out.println(Base64.encode(String.valueOf(new Date().getTime()).getBytes()));
		
		Set<String> usernames = new HashSet<String>();
		for(int i=0 ; i < 100000 ; i++){
			String username = pp.randomString(10);
			if(usernames.contains(username)){
				System.out.print(String.format("%s is already exsit", username));
			}else{
//				System.out.println("username is  " + username);
				usernames.add(username);
			}
		}
		
		System.out.print("Finished !");
	}

	@Override
	public String retrieveUsernameById(Integer id) {
		
		User user = userDao.fetchOneById(id);
		if(user != null){
			return user.getName();
		}
		return null;
	}

	@Override
	public Integer retrieveUserIdByFederationId(String federationId) {
		
		Integer userId = userFederationDao.fetchUserIdByFederationId(federationId);
		
		if(logger.isDebugEnabled()){
			logger.debug("The user {} has been retrieved from DB via federation id {}", userId, federationId);
		}
		
		return userId;
		
	}

}
