package cn.clubox.quiz.service.api.auth;

import java.io.UnsupportedEncodingException;

public interface OAuth2Authenticator {

	public static final String STATE = "clubox_cn";
	
	public String acquireAuthorizationCode(String appid, String scope, String redirectUri) throws UnsupportedEncodingException;
	
	public OAuth2AccessToken acquireAccessToken(String code, String appid, String secret);
	
	public WeChatUserInfo authenticate(String code, String appid, String secret);
}
