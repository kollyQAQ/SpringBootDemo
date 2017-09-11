package com.kolly.module.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by kolly on 2017/8/7.
 */
public class IpUtil {

	private static final Logger logger = LoggerFactory.getLogger(IpUtil.class);

	/**
	 * 获取请求客户端IP
	 * <p>
	 * wiki:http://www.cnblogs.com/ITtangtang/p/3927768.html
	 */
	public static String getClientIp(HttpServletRequest request) {

		logger.info("IpUtil.getClientIp X-Forwarded-For:{}", request.getHeader("X-Forwarded-For"));
		logger.info("IpUtil.getClientIp X-Real-IP:{}", request.getHeader("X-Real-IP"));
		logger.info("IpUtil.getClientIp RemoteAddr:{}", request.getRemoteAddr());

		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			//多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

	/**
	 * linux获取本地 IP Address
	 * <p>
	 * addr.getHostAddress() 输出IP
	 * addr.getHostName() 输出主机名
	 */
	public static InetAddress getAddress() {
		try {
			for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements(); ) {
				NetworkInterface networkInterface = interfaces.nextElement();
				if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
					continue;
				}
				Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
				if (addresses.hasMoreElements()) {
					return addresses.nextElement();
				}
			}
		} catch (SocketException e) {
			//ignore
		}
		return null;
	}

}
