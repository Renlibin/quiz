package cn.clubox.quiz.service.api;

public interface QuizUrlEncodeDecodeService {

	public String encode(String rowUrl);
	
	public String decode(String encodedUrl);
}
