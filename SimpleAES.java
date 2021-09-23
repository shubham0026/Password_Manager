package main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


class SimpleAES {
	static String encrypt(String message, String stringKey, String salt) {
		try {
			MessageDigest messageDigestMain = null;
			byte[] byteArrayKey;
			byteArrayKey = stringKey.getBytes("UTF-8");
			messageDigestMain = MessageDigest.getInstance("SHA-1");
			byteArrayKey = messageDigestMain.digest(byteArrayKey);
			byteArrayKey = Arrays.copyOf(byteArrayKey, 16);
			SecretKeySpec secretKey;
			secretKey = new SecretKeySpec(byteArrayKey, "AES");
			
			Cipher cipherMain = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipherMain.init(Cipher.ENCRYPT_MODE, secretKey);
			
		    String messageToEncrypt = null;
			String encryptedMessage = message;
			for (int i = 0; i < 2; i++) {
				messageToEncrypt = salt + encryptedMessage;
				byte[] tempEncryptedMessage = cipherMain.doFinal(messageToEncrypt.getBytes());
				encryptedMessage = new BASE64Encoder().encode(tempEncryptedMessage);
			}
			return encryptedMessage.replaceAll("\\s+","");
		}
		catch (Exception e) {
			ErrorLogger.log(ExtraFunctions.logNameToFilePath("main"), "Encryption error due to " + e.toString());
			return "";
		}
	}
	
	
	static String decrypt(String encryptedMessage, String stringKey, String salt) {
		try {
			MessageDigest sha = null;
			byte[] key;
			key = stringKey.getBytes("UTF-8");
			sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			SecretKeySpec secretKey;
			secretKey = new SecretKeySpec(key, "AES");
			
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
			
			String decryptedMessage = null;
	        String messageToDecrypt = encryptedMessage;
	        for (int i = 0; i < 2; i++) {
	            byte[] tempDecodedMessage = new BASE64Decoder().decodeBuffer(messageToDecrypt);
	            byte[] tempDecryptedMessage = cipher.doFinal(tempDecodedMessage);
	            decryptedMessage = new String(tempDecryptedMessage).substring(salt.length());
	            messageToDecrypt = decryptedMessage;
	        }
	        return decryptedMessage;			
		}
		catch (Exception e) {
			ErrorLogger.log(ExtraFunctions.logNameToFilePath("main"), "Decryption error due to " + e.toString());
			return "Error decrypting.";
		}
	}
	
	
	static String generateKey() {
		try {
			KeyGenerator keyGeneratorMain = KeyGenerator.getInstance("AES");
			keyGeneratorMain.init(128);
			
			SecretKey keyMain = keyGeneratorMain.generateKey();
			return Base64.getEncoder().encodeToString(keyMain.getEncoded());
		} catch (Exception e) {
			ErrorLogger.log(ExtraFunctions.logNameToFilePath("main"), "Key generation error due to " + e.toString());
			return "";
		}
	}
}
