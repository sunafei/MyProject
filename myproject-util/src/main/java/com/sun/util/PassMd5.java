package com.sun.util;

import java.security.MessageDigest;

/**
 * @author aujlure 加密
 */
public class PassMd5 {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/** 对字符串进行MD5加密 */
	public static String encodeByMD5(String originString) {
		if (originString == null) {
			return null;
		}
		
		try {
			return byteArrayToHexString(MessageDigest.getInstance("MD5").digest(originString.getBytes())).toUpperCase();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/** 将一个字节转化成十六进制形式的字符串 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
}