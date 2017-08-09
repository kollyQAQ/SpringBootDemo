package com.kolly.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

public class ShaUtil {

	private static final Logger logger = LoggerFactory.getLogger(ShaUtil.class);

	private static final String SHA = "SHA1";

	public static String encrypt(byte[] value) {
		if (value == null)
			return "";

		MessageDigest md = null;
		String strDes = null;

		try {
			md = MessageDigest.getInstance(SHA);
			md.update(value);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
		return strDes;
	}

	public static String bytes2Hex(byte[] byteArray) {
		StringBuffer strBuf = new StringBuffer();
		String tmp = null;
		for (int i = 0; i < byteArray.length; i++) {
			tmp = Integer.toHexString(byteArray[i] & 0xFF);
			if (tmp.length() == 1) {
				strBuf.append("0");
			}
			strBuf.append(tmp);
		}
		return strBuf.toString();
	}

	public static String shaSign(Map<String, String> map, String charset) {

		ArrayList<String> lst = new ArrayList<String>();
		String s = "";
		for (String k : map.keySet()) {
			lst.add(k);
		}

		Collections.sort(lst, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});

		for (int i = 0; i < lst.size(); i++) {
			String k = lst.get(i);
			String v = map.get(k);
			s += k + "=" + v + "&";
		}
		if (s.endsWith("&")) {
			s = s.substring(0, s.length() - 1);
		}
		String sign = "";
		try {
			logger.info("待SHA1加密字符串:" + s);
			sign = encrypt(s.getBytes(charset)).toUpperCase();
		} catch (UnsupportedEncodingException e) {
			logger.error("Error occurs during sha1 encrypt", e);
		}
		logger.info("SHA1加密结果：" + sign);
		return sign;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		String s = "abc";
		System.out.println(encrypt(s.getBytes("utf8")).toUpperCase());
	}
}
