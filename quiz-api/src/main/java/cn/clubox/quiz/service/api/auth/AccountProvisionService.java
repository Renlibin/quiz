package cn.clubox.quiz.service.api.auth;

public interface AccountProvisionService {

	public Integer provisionAccount(WeChatUserInfo userInfo);
	
	public String retrieveUsernameById(Integer userId);
	
}
