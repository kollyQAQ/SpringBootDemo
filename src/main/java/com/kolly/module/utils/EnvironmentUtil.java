package com.kolly.module.utils;

import org.apache.commons.lang3.StringUtils;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class EnvironmentUtil {

	private static Map<String, String> serverNameIP = new HashMap<>();

	static {
		serverNameIP.put("10.104.36.141", "WB-IDC0");
		serverNameIP.put("10.104.49.6", "WB-IDC1");
	}

	public static boolean isIDC() {
		return StringUtils.equals("idc", System.getProperty("cfg.env"));
	}

	public static boolean isLocal() {
		return StringUtils.equals("local", System.getProperty("cfg.env"));
	}

	public static String getSysAttr(String attrName) {
		return System.getProperty(attrName);
	}

	public static String getServerName() {

		InetAddress address = IpUtil.getAddress();

		if (address == null) {
			return "";
		}

		return serverNameIP.get(address.getHostAddress());
	}
}
