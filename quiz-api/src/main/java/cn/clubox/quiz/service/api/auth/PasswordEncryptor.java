package cn.clubox.quiz.service.api.auth;

public interface PasswordEncryptor {

	public String encrypt(String text) throws Exception;
	
	public String decrypt(String encryptText) throws Exception;
	 
}
