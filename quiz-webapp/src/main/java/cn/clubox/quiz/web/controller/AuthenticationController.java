package cn.clubox.quiz.web.controller;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.clubox.quiz.service.api.auth.AccountProvisionService;
import cn.clubox.quiz.service.api.auth.OAuth2Authenticator;
import cn.clubox.quiz.service.api.auth.SecurityService;
import cn.clubox.quiz.service.api.auth.WeChatUserInfo;
import cn.clubox.quiz.web.utils.OAuthConfig;

@Controller
public class AuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	private OAuth2Authenticator oAuth2Authenticator;
	
	@Autowired
    private SecurityService securityService;
	
	@Autowired
	private AccountProvisionService accountProvisionService;
	
	private String redirectUri = "http://www.rankbox.wang/rb/quiz/federation/callback";
	
	@Deprecated
	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		
		//If user is not in user_federation table
		//return "redirect:/quiz/federation/auth";
		
		//If user is in user_federation table
		securityService.autoLogin("u-ZufHm66TcTVk");
		DefaultSavedRequest savedRequest = (DefaultSavedRequest)request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
		
		if(logger.isDebugEnabled()){
			logger.debug("Redirect url is {}", savedRequest.getRedirectUrl());
		}
		
		return "redirect:".concat(savedRequest.getRedirectUrl());
	}
	
	
	@GetMapping("/quiz/federation/auth")
	public String auth(){
		
		logger.info("Firing OAuth2 authorization process");
		
		try {
			String codeAcquireUri = oAuth2Authenticator.acquireAuthorizationCode(OAuthConfig.KF_APPID,OAuthConfig.LOGIN_SCOPE,redirectUri);
			return "redirect:".concat(codeAcquireUri);
		} catch (UnsupportedEncodingException e) {
			logger.error("Could not acquire authorization code due to exception ", e.getMessage());
		}
		
		return "redirect:/login";
	}
	
	@GetMapping("/quiz/federation/callback")
	public String callback(HttpServletRequest request, @RequestParam(value="code", required= false) String code,
			@RequestParam(value="state", required=true) String state){
		
		if(logger.isDebugEnabled()){
			logger.debug("The code and state are {} and {} respectivly",code, state);
			
		}
		
		if(code == null || code.isEmpty()){
			return "redirect:/provison_failed";
		}
		
		//state should be verified
		if(OAuth2Authenticator.STATE.equals(state) == false){
			return "redirect:/provison_failed";
		}
		
		WeChatUserInfo userInfo = oAuth2Authenticator.authenticate(code,OAuthConfig.KF_APPID, OAuthConfig.KF_SECRET);
		Integer userId = accountProvisionService.provisionAccount(userInfo);
		String username = accountProvisionService.retrieveUsernameById(userId);
		
		if(username == null || username.isEmpty()){
			//Exception should be handled
		}
		securityService.autoLogin(username);
		
		DefaultSavedRequest savedRequest = (DefaultSavedRequest)request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
		
		if(logger.isDebugEnabled()){
			logger.debug("Redirect url is {}", savedRequest.getRedirectUrl());
		}
		
		return "redirect:".concat(savedRequest.getRedirectUrl());
	}
}
