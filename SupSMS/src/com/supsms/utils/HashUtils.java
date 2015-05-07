/*
 * Author : Lokicoule
 */
package com.supsms.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * Get String
 * Return Hashed String
 */
public class HashUtils {
	private HashUtils() {
	}

	public static String hashStringInSHA256(String str) {
		return hashString(str, "SHA-512");
	}

	public static String hashString(String str, String hashType) {
		byte[] digest;
		StringBuffer sb = new StringBuffer();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance(hashType);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		md.update(str.getBytes());
		digest = md.digest();
		for (int i = 0; i < digest.length; i++)
			sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16)
					.substring(1));
		return sb.toString();
	}
}
