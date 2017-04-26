package cn.clubox.quiz.service.impl.auth;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cn.clubox.quiz.service.api.auth.OAuth2Authenticator;
import cn.clubox.quiz.service.api.auth.WeChatUserInfo;

@Service
public class WeChatOAuth2Authenticator extends RestTemplate implements OAuth2Authenticator {
	
	private static final Logger logger = LoggerFactory.getLogger(WeChatOAuth2Authenticator.class);
	
	private static final String OPEN_ID = "openid";

//	private static final String UNION_ID = "unionid";
//	@Autowired
	private String appid = "wxc83e8232a3a40f6c";
	
//	@Autowired
	private String secret = "aa1986c9e5323833d4e23567ee2112ad";
	
	@Autowired
	private String redirectURI;

	@Override
	public WeChatUserInfo authenticate(String code) {

		OAuth2AccessToken accessToken = this.acquireAccessToken(code);
		
		if(accessToken == null){
			logger.info("Could not acquire access token");
			
			return null;
		}
		
		String tokenValue = accessToken.getValue();
		Map<String, Object> additionalInformation = accessToken.getAdditionalInformation();
		
		String openid;
		Object openidObj = additionalInformation.get(OPEN_ID);
		if(openidObj != null){
			openid = String.valueOf(openidObj);
		}else{
			logger.warn("Could not get openid from OAuth2AccessToken");
			return null;
		}
		
//		String unionid;
//		Object unionidObj = additionalInformation.get(UNION_ID);
//		if(unionidObj != null){
//			unionid = String.valueOf(unionidObj);
//		}
		
	    WeChatUserInfo userInfo = this.retrieveUserInfo(tokenValue, openid);
		
		return userInfo;
	}
	
	private OAuth2AccessToken acquireAccessToken(String code){
		
//		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		
		/**
		 * Response
		 * 
		 * {
			   "access_token":"ACCESS_TOKEN",
			   "expires_in":7200,
			   "refresh_token":"REFRESH_TOKEN",
			   "openid":"OPENID",
			   "scope":"SCOPE",
			   "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
			}
		 */
		
		StringBuilder uriBuilder = new StringBuilder();
		uriBuilder.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=").append(appid).append("&secret=")
			.append(secret).append("&code=").append(code).append("&grant_type=authorization_code");
		
		try {
			URI uri = new URI(uriBuilder.toString());
			ResponseEntity<DefaultOAuth2AccessToken> entity = super.exchange(uri, HttpMethod.GET, null, DefaultOAuth2AccessToken.class);
			
			OAuth2AccessToken accessToken = entity.getBody();
			
			return accessToken;
		} catch (URISyntaxException e) {
			logger.error("Could not acquire AccessToken due to error {}", e.getMessage());
		}
		
		return null;
	}
	
	private WeChatUserInfo retrieveUserInfo(String accessToken, String openid){
		
//		String url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		
		/**
		 * Response
		 * 
		 * {
			   "openid":" OPENID",
			   "nickname": NICKNAME,
			   "sex":"1",
			   "province":"PROVINCE"
			   "city":"CITY",
			   "country":"COUNTRY",
			   "headimgurl":    "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46", 
				"privilege":[
					"PRIVILEGE1"
					"PRIVILEGE2"
			    ],
			    "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL"
			}
		 */
		
		StringBuilder uriBuilder = new StringBuilder();
		uriBuilder.append("https://api.weixin.qq.com/sns/userinfo?access_token=").append(accessToken)
			.append("&openid=").append(openid).append("&lang=zh_CN");
		
		try {
			URI uri = new URI(uriBuilder.toString());
			ResponseEntity<WeChatUserInfo> entity = super.exchange(uri, HttpMethod.GET, null, WeChatUserInfo.class);
			
			logger.debug("The user information is {}",entity.toString());
			
			return entity.getBody();
		} catch (URISyntaxException e) {
			logger.error("User information could no be acquried due to the error {}", e.getMessage());
		}
		
		return null;
	}

	@Override
	public void acquireAuthorizationCode() {
		
//		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_base&state=clubox_cn#wechat_redirect";
		
		StringBuilder uriBuilder = new StringBuilder();
		uriBuilder.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=")
		.append(appid).append("&redirect_uri=").append(redirectURI)
		.append("&response_type=code&scope=snsapi_base&state=").append(OAuth2Authenticator.STATE).append("#wechat_redirect");
		
		if(logger.isDebugEnabled()){
			logger.debug("The authentication code acquiring URI is {}", uriBuilder.toString());
		}
		
		URI uri;
		try {
			uri = new URI(uriBuilder.toString());
			super.doExecute(uri, HttpMethod.GET, null, null);
		} catch (URISyntaxException e) {
			logger.error("Authentication code could not be acquired due to error {}", e.getMessage());
		}
	}
}
