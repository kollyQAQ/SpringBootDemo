package com.kolly.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kolly on 2017/8/8.
 */
public class Md5Util {

	private static final Logger logger = LoggerFactory.getLogger(Md5Util.class);

	private static final String MD5 = "MD5";

	public static String encryptString(String oriStr) {

		logger.info("Md5Util.encryptString oriStr:{}", oriStr);

		try {
			MessageDigest md = MessageDigest.getInstance(MD5);
			md.update(oriStr.getBytes("UTF-8"));
			byte b[] = md.digest();
			int i;

			StringBuilder buf = new StringBuilder();
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) {
					i += 256;
				}
				if (i < 16) {
					buf.append("0");
				}
				buf.append(Integer.toHexString(i));
			}

			String md5 = buf.toString().toUpperCase();
			logger.info("Md5Util.encryptString md5:{}", md5);

			return md5;

		} catch (Exception e) {
			logger.error("Error occur during Md5Util.encrypt", e);
		}

		return null;
	}

	public static String encryptMap(Map<String, String> map, String key) {

		logger.info("Md5Util.encryptMap map:{} key:{}", map.toString(), key);

		if (map == null || CollectionUtils.isEmpty(map) || key == null || key.length() == 0) {
			return null;
		}

		ArrayList<String> lst = new ArrayList<>();
		lst.addAll(map.keySet());

		Collections.sort(lst, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});

		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < lst.size(); i++) {
			String k = lst.get(i);
			String v = map.get(k);
			sb.append(k).append("=").append(v).append("&");
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
		System.out.println(Md5Util.encryptMap(map, "eikdfnk234123"));
	}

}
