package cn.clubox.quiz.web.config;

import javax.sql.DataSource;

import org.jooq.Configuration;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DefaultConfiguration;
import org.jooq.impl.DefaultDSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * Quiz WAR application
 */
@SpringBootApplication
//@PropertySource(value = { "WEB-INF/custom.properties" })
@ComponentScan(" cn.clubox.quiz.service.impl,cn.clubox.quiz.service.impl.dao, "
		+ "cn.clubox.quiz.web.auth, cn.clubox.quiz.web.controller, "
		+ "cn.clubox.quiz.web.utils")
public class QuizWarApplication extends SpringBootServletInitializer {
	
	private static final Logger logger = LoggerFactory.getLogger(QuizWarApplication.class);
       
//       @Override
 //   protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
  //      return application.sources(SampleWarApplication.class);
   // }
	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		
		if(logger.isDebugEnabled()){
			logger.debug("****** Setting external configuration file ******");
		}
	
		return application.sources(QuizWarApplication.class)
				.properties("spring.config.name:config")
				.properties("spring.config.location:file:///opt/apps/appconfig/");
	}

	public static void main(String[] args) {
		SpringApplication.run(QuizWarApplication.class, args);
	}
	
	@Bean
    public DSLContext dsl(org.jooq.Configuration config) {
		
		logger.info("SQL dialect is {}",config.dialect().getName());
		
		return new DefaultDSLContext(config);
    }
	
	@Bean
	public Configuration jooqConfig(DataSource dataSource){
		
		Configuration config = new DefaultConfiguration().derive(dataSource).derive(SQLDialect.MYSQL);

		logger.info("Configuration is {}", config);
		
		return config;
		 
	}
	
	@ConfigurationProperties(prefix = "spring.datasource")
	@Bean(name = "dataSource")
	public DataSource dataSource(){
		return DataSourceBuilder
		        .create()
		        .build();
	}
	
	@Bean
    public ViewResolver viewResolver() {
		
		logger.info("FreeMarker View Resolver is going to be initialized");
		
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        resolver.setPrefix("");
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setRequestContextAttribute("rc");
        return resolver;
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

