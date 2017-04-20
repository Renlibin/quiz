package cn.clubox.quiz.service.impl.auth;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;

import cn.clubox.quiz.service.api.auth.PasswordEncryptor;

@Service
public class AESPasswordEncryptor implements PasswordEncryptor {

	private static final Logger logger = LoggerFactory.getLogger(AESPasswordEncryptor.class);

	private static final String KEY = "cluboxcncluboxcn"; // 128 bit key
	private static final String INIT_VECTOR = "StudyHard321-qaz"; // 16 bytes IV

	private static Cipher encryptCipher;
	private static Cipher decryptCipher;

	{
		try {
			IvParameterSpec iv = new IvParameterSpec(INIT_VECTOR.getBytes("UTF-8"));
			SecretKeySpec skeySpec = new SecretKeySpec(KEY.getBytes("UTF-8"), "AES");
			encryptCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			encryptCipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			decryptCipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
			decryptCipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

		} catch (InvalidKeyException | InvalidAlgorithmParameterException | NoSuchAlgorithmException
				| NoSuchPaddingException | UnsupportedEncodingException e) {
			logger.error("Ciphers could not be initialized due to error {}", e.getMessage());
		}
	}

	@Override
	public String encrypt(String value) {
		try {

			byte[] encrypted = encryptCipher.doFinal(value.getBytes());

			if (logger.isDebugEnabled()) {
				logger.debug("Encrypted password is {}", new String(Base64.encode(encrypted)));
			}

			return new String(Base64.encode(encrypted));
		} catch (IllegalBlockSizeException | BadPaddingException ex) {
			logger.error("Password could not be encryped due to error {}", ex.getMessage());
		}

		return null;
	}

	@Override
	public String decrypt(String encrypted) {
		try {

			byte[] original = decryptCipher.doFinal(Base64.decode(encrypted.getBytes()));

			if (logger.isDebugEnabled()) {
				logger.debug("Decrypted password is {}", new String(original));
			}

			return new String(original);
		} catch (IllegalBlockSizeException | BadPaddingException ex) {
			logger.error("Password could not be encrypted due to error {}", ex.getMessage());
		}

		return null;
	}

	public static void main(String[] args) {
		String key = "Bar12345Bar12345"; // 128 bit key
		String initVector = "RandomInitVector"; // 16 bytes IV

		AESPasswordEncryptor encryptor = new AESPasswordEncryptor();

		System.out.println(encryptor.decrypt(encryptor.encrypt("courage123")));
	}

}
