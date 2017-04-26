package cn.clubox.quiz.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import cn.clubox.quiz.service.api.auth.AccountProvisionService;
import cn.clubox.quiz.service.api.auth.OAuth2Authenticator;
import cn.clubox.quiz.service.api.auth.SecurityService;
import cn.clubox.quiz.service.api.auth.WeChatUserInfo;

@Controller
public class AuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	private OAuth2Authenticator oAuth2Authenticator;
	
	@Autowired
    private SecurityService securityService;
	
	@Autowired
	private AccountProvisionService accountProvisionService;
	
	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		
		//If user is not in user_federation table
		//return "redirect:/quiz/federation/auth";
		
		//If user is in user_federation table
		securityService.autoLogin("mike");
		DefaultSavedRequest savedRequest = (DefaultSavedRequest)request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
		
		if(logger.isDebugEnabled()){
			logger.debug("Redirect url is {}", savedRequest.getRedirectUrl());
		}
		
		return "redirect:".concat(savedRequest.getRedirectUrl());
	}
	
	
	@GetMapping("/quiz/federation/auth")
	@ResponseStatus(value = HttpStatus.OK)
	public void auth(){
		
		logger.info("Firing OAuth2 authorization process");
		
		oAuth2Authenticator.acquireAuthorizationCode();
	}
	
	@GetMapping("/quiz/federation/callback")
	public String callback(@RequestParam(value="code", required= false) String code, @RequestParam(value="state", required=true) String state){
		
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
		
		WeChatUserInfo userInfo = oAuth2Authenticator.authenticate(code);
		
		accountProvisionService.provisionAccount(userInfo);
		
		return "redirect:/login";
	}
}
