package cn.clubox.quiz.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import cn.clubox.quiz.service.api.QuizUrlEncodeDecodeService;

@Controller
public class UrlEncodeDecodeController {

	private static Logger logger = LoggerFactory.getLogger(UrlEncodeDecodeController.class);
	
	@Autowired
	private QuizUrlEncodeDecodeService quizUrlEncodeDecodeService;
	
	@GetMapping("/quiz/url/encode")
	public String getEncode(){
		
		if(logger.isDebugEnabled()){
			logger.debug("UrlEncoderController.getEncode -> Getting encoder page");
		}
		
		return "url_encoder";
	}
	
	@PostMapping("/quiz/url/encode")
	public String postEncode(@RequestBody String url, Model model){
		
		if(logger.isDebugEnabled()){
			logger.debug("UrlEncoderController.getEncode -> External quiz url is {}", url);
		}
		
		String encodedUrl = quizUrlEncodeDecodeService.encode(url);
			
		model.addAttribute("encodedUrl", encodedUrl);
		return "url_encoder";
	}
	
	@PostMapping("/quiz/url/decode")
	public String postDecode(@RequestBody String url, Model model){
		
		String decodedUrl = quizUrlEncodeDecodeService.decode(url);

		model.addAttribute("decodedUrl",decodedUrl);
		return "url_encoder";
	}
	
	
}
