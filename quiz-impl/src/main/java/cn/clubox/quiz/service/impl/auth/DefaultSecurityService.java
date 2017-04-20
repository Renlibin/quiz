package cn.clubox.quiz.service.impl.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.service.api.auth.SecurityService;

@Service
public class DefaultSecurityService implements SecurityService {

	private static final Logger logger = LoggerFactory.getLogger(DefaultSecurityService.class);

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	public void autoLogin(String username) {
		
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		
		if(logger.isDebugEnabled()){
			logger.debug("User {} password is {}", username, userDetails.getPassword());
		}
		
//		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
//				userDetails, password, userDetails.getAuthorities());
		
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				userDetails, userDetails.getPassword(), userDetails.getAuthorities());
		
		logger.debug("AuthenticationManager class name is {}", authenticationManager.getClass().getName());
		
		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			logger.info("Auto login {} successfully!", username);
		}
	}

	public UserDetails getUserDetails() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails;
	}
}
