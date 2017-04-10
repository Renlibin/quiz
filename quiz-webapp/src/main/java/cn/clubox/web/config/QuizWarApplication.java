package cn.clubox.web.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.ComponentScan;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import javax.servlet.*;

/**
 * Quiz WAR application
 */
@SpringBootApplication
//@PropertySource(value = { "WEB-INF/custom.properties" })
@ComponentScan("cn.clubox.web.auth, cn.clubox.web.controller")
public class QuizWarApplication extends SpringBootServletInitializer {
       
//       @Override
 //   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
  //      return application.sources(SampleWarApplication.class);
   // }

	public static void main(String[] args) {
		SpringApplication.run(QuizWarApplication.class, args);
	}
    
//	@Bean
//    public FilterRegistrationBean greetingFilterRegistrationBean(){
//
//        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
//        registrationBean.setName("greeting");
//        GreetingFilter greetingFilter = new GreetingFilter();
//        registrationBean.setFilter(greetingFilter);
//        registrationBean.addUrlPatterns("/");
//        registrationBean.setOrder(1);
//        return registrationBean;
//    } 
}

