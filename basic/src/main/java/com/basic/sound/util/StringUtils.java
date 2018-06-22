package com.basic.sound.util;

import java.security.MessageDigest;

public class StringUtils {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	/**
	 * md5加密
	 * 
	 * @param source
	 *            加密字符串
	 * @return
	 */
	public static String md5Encode(String source) {
		String result = null;
		try {
			result = source;
			// 获得MD5摘要对象
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			// 使用指定的字节数组更新摘要信息
			messageDigest.update(result.getBytes("utf-8"));
			// messageDigest.digest()获得16位长度
			result = byteArrayToHexString(messageDigest.digest());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String byteArrayToHexString(byte[] bytes) {
		StringBuilder stringBuilder = new StringBuilder();
		for (byte tem : bytes) {
			stringBuilder.append(byteToHexString(tem));
		}
		return stringBuilder.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
}
