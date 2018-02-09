package cn.clubox.quiz.service.impl;

import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.service.api.QuizUrlEncodeDecodeService;

@Service
public class DefaultQuizUrlEncodeDecodeService implements QuizUrlEncodeDecodeService {

	private static final Logger logger = LoggerFactory.getLogger(DefaultQuizUrlEncodeDecodeService.class);
	
	// some random salt
	private static final byte[]	SALT = { (byte) 0x21, (byte) 0x21, (byte) 0xF0, (byte) 0x55, (byte) 0xC3, (byte) 0x9F, (byte) 0x5A, (byte) 0x75};
	private final static int	ITERATION_COUNT	= 31;
	
	@Override
	public String encode(String rowUrl) {

		if(Objects.isNull(rowUrl)){
			return "";
		}
		
		try
		{

			KeySpec keySpec = new PBEKeySpec(null, SALT, ITERATION_COUNT);
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, ITERATION_COUNT);

			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

			Cipher ecipher = Cipher.getInstance(key.getAlgorithm());
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);

			byte[] enc = ecipher.doFinal(rowUrl.getBytes());

			String res = new String(Base64.encode(enc));
			// escapes for url
			res = res.replace('+', '-').replace('/', '_').replace("%", "%25").replace("\n", "%0A");

			return res;

		}
		catch (Exception e)
		{
			
		}
		
		return null;
	}

	@Override
	public String decode(String encodedUrl) {
		
		if(Objects.isNull(encodedUrl)){
			return "";
		}
		try{
			String input = encodedUrl.replace("%0A", "\n").replace("%25", "%").replace('_', '/').replace('-', '+');
	
			byte[] dec = Base64.decode(input.getBytes());
	
			KeySpec keySpec = new PBEKeySpec(null, SALT, ITERATION_COUNT);
			AlgorithmParameterSpec paramSpec = new PBEParameterSpec(SALT, ITERATION_COUNT);
	
			SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
	
			Cipher dcipher = Cipher.getInstance(key.getAlgorithm());
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
	
			byte[] decoded = dcipher.doFinal(dec);
	
			String result = new String(decoded);
			return result;
		}catch(Exception e){
			
		}
		return null;
	}
	
	public static void main(String[] args){
		DefaultQuizUrlEncodeDecodeService es = new DefaultQuizUrlEncodeDecodeService();
		
		String encodedUrl = es.encode("http://ce.rankbox.wang/handler/jqemed.ashx?activity=19023164&width=760&source=iframe");
		System.out.println("Encoded url is " + encodedUrl);
//		String decodedUrl = es.decode("lTrduLGV2uJtQ2oILQKCsJROJJnmEUUymq4srGd18lbSF2yhb8YdvML1XmBKKoQru2fjWR5-8bxxcjf31Jk3vBaJhop2BuQvCN_OSIvJE2DEHvg5amTOGvsvxjjUw2EN_PbS0KgPsL8=");
//		System.out.println("Decoded url is " + decodedUrl);
	}

}
