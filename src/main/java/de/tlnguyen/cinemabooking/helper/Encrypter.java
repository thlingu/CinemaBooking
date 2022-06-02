package de.tlnguyen.cinemabooking.helper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter {
	
	private static Encrypter instance;
	
	private Encrypter() {
	}
	
	public static synchronized Encrypter getInstance() {
		if (instance == null) {
			instance = new Encrypter();
		}
		
		return instance;
	}
	
	public String encryptToSha256HashHexString(String strStringToEncrypt) throws Exception {
		if (strStringToEncrypt.isEmpty()) {
			throw new Exception("String is empty!");
		} else if (strStringToEncrypt.isBlank()) {
			throw new Exception("String contains only white spaces!");
		} else {
			String strHexString = "";
			
			try {
				MessageDigest digest           = MessageDigest.getInstance("SHA-256");
				byte[]        hash             = digest.digest(strStringToEncrypt.getBytes(StandardCharsets.UTF_8));
				StringBuffer  hexStringBuilder = new StringBuffer();
				
				for (int i = 0; i < hash.length; ++i) {
					String strSingleByteHash = Integer.toHexString(255 & hash[i]);
					if (strSingleByteHash.length() == 1) {
						hexStringBuilder.append('0');
					}
					
					hexStringBuilder.append(strSingleByteHash);
				}
				
				strHexString = hexStringBuilder.toString();
			} catch (NoSuchAlgorithmException var8) {
				var8.printStackTrace();
			}
			
			return strHexString;
		}
	}
}