package cn.clubox.quiz.service.impl.auth;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.jooq.domain.tables.pojos.User;
import cn.clubox.quiz.service.api.auth.AccountProvisionService;
import cn.clubox.quiz.service.api.auth.WeChatUserInfo;
import cn.clubox.quiz.service.impl.dao.UserDaoExt;

@Service
public class DynamicAccountProvisionService implements AccountProvisionService {

	private static final Logger logger = LoggerFactory.getLogger(DynamicAccountProvisionService.class);

	@Autowired
	private UserDaoExt userDao;
	
	private static String USERNAME_PREFIX = "u-";
	private static short  USERNAME_LENGTH = 12;
	private static short  PASSWORD_LENGTH = 6;

	@Override
	public void provisionAccount(WeChatUserInfo userInfo) {
		
		String username = randomString(USERNAME_LENGTH);
		String password = randomString(PASSWORD_LENGTH);
		username = USERNAME_PREFIX.concat(username);
		
		User user = new User();
		user.setName(username);
		user.setNickname(userInfo.getNickname());
		user.setPassword(password);
		user.setPortraitSrc(userInfo.getHeadimgurl());
		user.setStatus("Y");
		
		boolean isSuccess = false;
		while(!isSuccess){
			try{
				int userId = userDao.insertWithReturning(user);
				isSuccess = true;
			}catch(Exception e){
				
				logger.warn("The username {} is already exist in DB", user.getName());
				//New username should be generated while the previous username is already exist in DB
				user.setName(USERNAME_PREFIX.concat(randomString(USERNAME_LENGTH)));
			}
		}
		//user_federation and user_source should be persisted as well

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

}
