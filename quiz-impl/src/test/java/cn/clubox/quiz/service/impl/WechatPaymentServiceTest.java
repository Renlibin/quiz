package cn.clubox.quiz.service.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cn.clubox.quiz.service.impl.payment.WechatPaymentService;

//@Ignore
//@SpringBootTest
public class WechatPaymentServiceTest {
	
	private static Logger logger = LoggerFactory.getLogger(WechatPaymentServiceTest.class);

	@Autowired
	private WechatPaymentService wechatPaymentService = new WechatPaymentService();
	
	@Test
	public void testPrePayment(){
		
		try {
			
//			Map<String,String> result = wechatPaymentService.prePayment("", "", BigDecimal.ZERO);
			
//			for(String key : result.keySet()){
//				System.out.println(String.format("%s = %s",  key, result.get(key)));
////				logger.info("{} = {}", key, result.get(key));
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
