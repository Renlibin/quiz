package cn.clubox.quiz.web.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userSecurityService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web.ignoring().antMatchers("/quiz/wxpay/notify");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/", "/home",
					"/quiz/federation/callback").permitAll()
//			.antMatchers(HttpMethod.POST, "/quiz/wxpay/notify").permitAll()
//			.anyRequest().authenticated()
			.and()
			.authorizeRequests().anyRequest().authenticated()
			.and()
			.formLogin().loginPage("/quiz/federation/auth").permitAll()
//			.formLogin().loginPage("/login").permitAll()
//			.and().formLogin()
			.and()
			.logout().logoutSuccessUrl("/")
            .logoutUrl("/logout")
            .deleteCookies("JSESSIONID")
            .permitAll()
			.and()
			.rememberMe().tokenValiditySeconds(3600);
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		
		//To set customizing UserSecuritySerivce to DaoAuthenticationProvider
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userSecurityService);
		 
		auth.inMemoryAuthentication().withUser("user").password("password").roles("SUPERUSER");
		auth.authenticationProvider(provider);
	}
}
