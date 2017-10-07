package com.kolly.common.utils.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Sha1Util {

	private static final Logger logger = LoggerFactory.getLogger(Sha1Util.class);

	public static String encryptString(String oriStr) {

		logger.info("Sha1Util.encryptString oriStr:{}", oriStr);

		try {
			// 生成一个SHA1加密计算摘要
			MessageDigest md = MessageDigest.getInstance("SHA1");

			// 计算sha1函数
			md.update(oriStr.getBytes());

			// digest()最后确定返回md5 hash值，返回值为8为字符串
			// 因为sha1 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			String md5 = new BigInteger(1, md.digest()).toString(16);

			logger.info("Sha1Util.encryptString md5:{}", md5);
			return md5;

		} catch (Exception e) {
			logger.error("Error occur during Sha1Util.encrypt", e);
		}

		return null;
	}

	public static String encryptMap(Map<String, String> map) {

		if (map == null || CollectionUtils.isEmpty(map)) {
			return null;
		}

		ArrayList<String> list = new ArrayList<>();
		list.addAll(map.keySet());
		//list.sort((o1, o2) -> o1.compareTo(o2));

		StringBuilder sb = new StringBuilder();
		for (String k : list) {
			sb.append(k).append("=").append(map.get(k)).append("&");
		}

		return encryptString(sb.toString().substring(0, sb.toString().length() - 1));
	}

	public static String encryptMapWithKey(Map<String, String> map, String key) {

		if (map == null || CollectionUtils.isEmpty(map) || key == null || key.length() == 0) {
			return null;
		}

		ArrayList<String> list = new ArrayList<>();
		list.addAll(map.keySet());
		//list.sort((o1, o2) -> o1.compareTo(o2));

		StringBuilder sb = new StringBuilder();
		for (String k : list) {
			sb.append(k).append("=").append(map.get(k)).append("&");
		}
		sb.append("key=").append(key);

		return encryptString(sb.toString());
	}

	public static void main(String[] args) {
		System.out.println(Sha1Util.encryptString("123456"));
		Map<String, String> map = new HashMap<>();
		map.put("name", "kolly");
		map.put("age", "12");
		map.put("school", "beijing");
		System.out.println(Sha1Util.encryptMap(map));
		System.out.println(Sha1Util.encryptMapWithKey(map, "eikdfnk234123"));
	}
}
