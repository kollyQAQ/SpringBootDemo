package com.kolly.common.web;

import com.kolly.common.BasicResult;
import com.kolly.common.mapper.JsonMapper;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kolly
 */
public class BaseWebService {

	private static final Logger logger = LoggerFactory.getLogger(BaseWebService.class);

	public static void responseJson(HttpServletResponse response, BasicResult result, int status) {

		String json = new JsonMapper().toJson(result);
		ServletOutputStream os = null;

		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/json");
			response.setStatus(status);
			os = response.getOutputStream();
			os.write(json.getBytes("UTF-8"));
		} catch (IOException e) {
			// ignore
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}

	}

	public static void responseText(HttpServletResponse response, String result, int status) {

		ServletOutputStream os = null;

		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			response.setStatus(status);
			os = response.getOutputStream();
			os.write(result.getBytes("utf-8"));
			os.flush();
		} catch (IOException e) {
			// ignore
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// ignore
				}
			}
		}
	}

	protected String getRequestBody(HttpServletRequest request) {
		try {
			InputStream is = request.getInputStream();
			List<String> lines = IOUtils.readLines(is, Charset.forName("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (String line : lines) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			logger.error("Error occurs during BaseWebService.getRequestBody", e);
		}
		return "";
	}

	protected List<String> getRequestBodyLines(HttpServletRequest request) {
		try {
			InputStream is = request.getInputStream();
			return IOUtils.readLines(is, Charset.forName("UTF-8"));
		} catch (Exception e) {
			logger.error("Error occurs during BaseWebService.getRequestBodyLines", e);
		}
		return null;
	}

	protected static String urlDecode(String param) {
		if (StringUtils.isEmpty(param)) {
			return "";
		}

		try {
			param = URLDecoder.decode(param, "UTF-8");
		} catch (Exception e1) {
			// ignore
		}

		return param;
	}

	public static void setCookie(HttpServletResponse response, String key, Object value, int maxAge) {
		if (StringUtils.isNotEmpty(key) && value != null) {
			try {
				Cookie cookie = new Cookie(key, URLEncoder.encode(String.valueOf(value), "utf-8"));
				cookie.setPath("/");
				cookie.setMaxAge(maxAge);
				response.addCookie(cookie);
			} catch (Exception e) {
				logger.error("Error occurs during BaseWebService.setCookie", e);
			}
		}
	}

	/**
	 * 从cookie或者header中获取指定数据
	 *
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getValueFromHeaderOrCookie(HttpServletRequest request, String key) {

		// 获取 uin 和 token 优先从 header 中查询 然后再从 cookie 中查询
		Map<String, Cookie> cookies = getCookies(request);

		// 设置
		String value = request.getHeader(key);
		if (StringUtils.isEmpty(value) && cookies.get(key) != null) {
			value = cookies.get(key).getValue();
		}

		return value;
	}

	private static Map<String, Cookie> getCookies(HttpServletRequest request) {
		Map<String, Cookie> map = new HashMap<>();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie != null && StringUtils.isNotEmpty(cookie.getName())) {
					map.put(cookie.getName(), cookie);
				}
			}
		}
		return map;
	}
}
