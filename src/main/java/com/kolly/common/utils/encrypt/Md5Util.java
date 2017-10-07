package com.kolly.common.utils.encrypt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kolly on 2017/8/8.
 */
public class Md5Util {

	private static final Logger logger = LoggerFactory.getLogger(Md5Util.class);

	public static String encryptString(String oriStr) {

		logger.info("Md5Util.encryptString oriStr:{}", oriStr);

		try {
			// 生成一个MD5加密计算摘要
			MessageDigest md = MessageDigest.getInstance("MD5");

			// 计算md5函数
			md.update(oriStr.getBytes());

			// digest()最后确定返回md5 hash值，返回值为8为字符串
			// 因为md5 hash值是16位的hex值，实际上就是8位的字符
			// BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
			String md5 = new BigInteger(1, md.digest()).toString(16);

			logger.info("Md5Util.encryptString md5:{}", md5);
			return md5;

		} catch (Exception e) {
			logger.error("Error occur during Md5Util.encrypt", e);
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
		System.out.println(Md5Util.encryptString("123456"));
		Map<String, String> map = new HashMap<>();
		map.put("name", "kolly");
		map.put("age", "12");
		map.put("school", "beijing");
		System.out.println(Md5Util.encryptMap(map));
		System.out.println(Md5Util.encryptMapWithKey(map, "eikdfnk234123"));
	}

}
