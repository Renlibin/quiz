package cn.clubox.quiz.service.impl.auth;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cn.clubox.quiz.service.api.auth.OAuth2AccessToken;
import cn.clubox.quiz.service.api.auth.OAuth2Authenticator;
import cn.clubox.quiz.service.api.auth.WeChatUserInfo;

@Service
public class WeChatOAuth2Authenticator extends RestTemplate implements OAuth2Authenticator {

	private static final Logger logger = LoggerFactory.getLogger(WeChatOAuth2Authenticator.class);

	private static final String OPEN_ID = "openid";

	private static final String UNION_ID = "unionid";

	// @Autowired
//	private String appid = "wx89032658cbf55f3c";
//	private String secret = "307824a578ea635fe6237d24997980da";

//	private String redirectURI = "http://www.rankbox.wang/rb/quiz/federation/callback";

	{
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();        
        //Add the Jackson Message converter
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		// Note: here we are making this converter to process any kind of response, 
		// not only application/*json, which is the default behaviour
		converter.setSupportedMediaTypes(Arrays.asList(MediaType.ALL));         
		messageConverters.add(converter);  
		this.setMessageConverters(messageConverters);
	}

	@Override
	public WeChatUserInfo authenticate(String code, String appid, String secret) {

		OAuth2AccessToken accessToken = this.acquireAccessToken(code, appid, secret);

		if (accessToken == null) {
			logger.info("Could not acquire access token");
			return null;
		}

		String tokenValue = accessToken.getAccess_token();
		// Map<String, Object> additionalInformation =
		// accessToken.getAdditionalInformation();

		// String openid;
		// Object openidObj = additionalInformation.get(OPEN_ID);
		String openid = accessToken.getOpenid();
		// if(openidObj != null){
		// openid = String.valueOf(openidObj);
		// }else{
		// logger.warn("Could not get openid from OAuth2AccessToken");
		// return null;
		// }

		// String unionid
		// Object unionidObj = additionalInformation.get(UNION_ID);
		// if(unionidObj != null){
		// unionid = String.valueOf(unionidObj);
		// }

		WeChatUserInfo userInfo = this.retrieveUserInfo(tokenValue, openid);

		return userInfo;
	}

	public OAuth2AccessToken acquireAccessToken(String code, String appid, String secret) {

		// String url =
		// "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

		/**
		 * Response
		 * 
		 * { "access_token":"ACCESS_TOKEN", "expires_in":7200,
		 * "refresh_token":"REFRESH_TOKEN", "openid":"OPENID", "scope":"SCOPE",
		 * "unionid": "o6_bmasdasdsad6_2sgVt7hMZOPfL" }
		 */

		StringBuilder uriBuilder = new StringBuilder();
		uriBuilder.append("https://api.weixin.qq.com/sns/oauth2/access_token?appid=").append(appid).append("&secret=")
				.append(secret).append("&code=").append(code).append("&grant_type=authorization_code");

		logger.debug("URI ===== " + uriBuilder.toString());

		/*
		 * {"access_token":
		 * "AmCsD5cRGreB48g-vvnlDA9iXGTwrLebR0vFuELeTHB6jP7tuYCRo2D4sd73s9eUut3ctZOzY7X2wU42lwGdmA",
		 * "expires_in":7200,"refresh_token":
		 * "GxU-S087nkaDqXxlz_Ysn4eY6ApO-7Oe7Z4IzRrUYqA3am4RLtv1Kp84Chm6YOzsCsrxGgBhIyVMA5O6zErwOQ",
		 * "openid":"opo_Y0mCPSAatwdUwqMyrL9LSzCY","scope":"snsapi_login",
		 * "unionid":"oO7rr1JMGALuRGzgV5gn4T9ei29c"}
		 */

		try {
			URI uri = new URI(uriBuilder.toString());
			// ResponseEntity<DefaultOAuth2AccessToken> entity =
			// super.exchange(uri, HttpMethod.GET, null,
			// DefaultOAuth2AccessToken.class);
			ResponseEntity<OAuth2AccessToken> accessToken = super.exchange(uri, HttpMethod.GET, null,
					OAuth2AccessToken.class);

			logger.debug("accessToken is {} ", accessToken.getBody().toString());

			// OAuth2AccessToken accessToken = entity.getBody();

			return accessToken.getBody();
		} catch (URISyntaxException e) {
			logger.error("Could not acquire AccessToken due to error {}", e.getMessage());
		}

		return null;
	}

	private WeChatUserInfo retrieveUserInfo(String accessToken, String openid) {

		// String url =
		// "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

		/**
		 * Response
		 * 
		 * { "openid":" OPENID", "nickname": NICKNAME, "sex":"1",
		 * "province":"PROVINCE" "city":"CITY", "country":"COUNTRY",
		 * "headimgurl":
		 * "http://wx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/46",
		 * "privilege":[ "PRIVILEGE1" "PRIVILEGE2" ], "unionid":
		 * "o6_bmasdasdsad6_2sgVt7hMZOPfL" }
		 */

		StringBuilder uriBuilder = new StringBuilder();
		uriBuilder.append("https://api.weixin.qq.com/sns/userinfo?access_token=").append(accessToken).append("&openid=")
				.append(openid).append("&lang=zh_CN");

		try {
			URI uri = new URI(uriBuilder.toString());
			ResponseEntity<WeChatUserInfo> entity = super.exchange(uri, HttpMethod.GET, null, WeChatUserInfo.class);

			logger.debug("The user information is {}", entity.getBody().toString());

			return entity.getBody();
		} catch (URISyntaxException e) {
			logger.error("User information could no be acquried due to the error {}", e.getMessage());
		}

		return null;
	}

	//https://open.weixin.qq.com/connect/oauth2/authorize?appid=appid&redirect_uri=url&response_type=code&scope=snsapi_base&state=park#wechat_redirect
	@Override
	public String acquireAuthorizationCode(String appid,String scope, String redirectUri) throws UnsupportedEncodingException {

		StringBuilder uriBuilder = new StringBuilder();
		uriBuilder.append("https://open.weixin.qq.com/connect/oauth2/authorize?appid=").append(appid)
				.append("&redirect_uri=").append(URLEncoder.encode(redirectUri, "UTF-8"))
				.append("&response_type=code&scope=").append(scope).append("&state=").append(OAuth2Authenticator.STATE)
				.append("#wechat_redirect");

		if (logger.isDebugEnabled()) {
			logger.debug("The authentication code acquiring URI is {}", uriBuilder.toString());
		}

		return uriBuilder.toString();
	}
}
