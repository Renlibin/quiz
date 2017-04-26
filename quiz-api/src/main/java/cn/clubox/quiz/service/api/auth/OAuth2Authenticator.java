package cn.clubox.quiz.service.api.auth;

public interface OAuth2Authenticator {

	public static final String STATE = "clubox_cn";
	
	public void acquireAuthorizationCode();
	
	public WeChatUserInfo authenticate(String code);
}
