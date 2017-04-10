package cn.clubox.quiz.service.api.auth;

public interface AccountProvisionService {

	public void provisionAccount(String userId, String userName, String unionId, String type);
	
}
