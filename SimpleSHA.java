package main;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

class SimpleSHA {
	static String SHA256SecureMessage(String message, String stringSalt) {
		String securedMessage = null;
		
		try {
			byte[] bytesArraySalt = stringSalt.getBytes();
			MessageDigest messageDigestMain = MessageDigest.getInstance("SHA-256");
			messageDigestMain.update(bytesArraySalt);
			byte[] bytesArrayMessage = messageDigestMain.digest(message.getBytes());
			StringBuilder stringBuilderMain = new StringBuilder();
			
			for (int i = 0; i < bytesArraySalt.length; i++) {
				stringBuilderMain.append(Integer.toString((bytesArrayMessage[i] & 0xff) + 0x100, 16).substring(1));
			}
			
			securedMessage = stringBuilderMain.toString();
			
			return securedMessage;
		} catch (Exception e) {
			ErrorLogger.log(ExtraFunctions.logNameToFilePath("main"), "Hashing error due to " + e.toString());
		}
		
		return "";
	}
	
	static String generateSalt() { 
		SecureRandom secureRandomMain;
		try {
			secureRandomMain = SecureRandom.getInstance("SHA1PRNG");
			
			byte[] byteArraySalt = new byte[16];
			secureRandomMain.nextBytes(byteArraySalt);
			
			return new String(byteArraySalt);
		} catch (NoSuchAlgorithmException e) {
			ErrorLogger.log(ExtraFunctions.logNameToFilePath("main"), "Salt generation error due to " + e.toString());
		}
		
		return "";
	}
}
