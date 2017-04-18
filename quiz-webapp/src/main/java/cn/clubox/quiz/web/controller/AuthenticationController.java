package cn.clubox.quiz.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import cn.clubox.quiz.service.api.auth.SecurityService;

@Controller
public class AuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
    private SecurityService securityService;
	
	@GetMapping("/login")
	public String login(HttpServletRequest request) {
		
		//If user in user_federation table
		
		securityService.autoLogin("mike");
		DefaultSavedRequest savedRequest = (DefaultSavedRequest)request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
		
		if(logger.isDebugEnabled()){
			logger.debug("Redirect url is {}", savedRequest.getRedirectUrl());
		}
		
		return "redirect:".concat(savedRequest.getRedirectUrl());
	}
}
