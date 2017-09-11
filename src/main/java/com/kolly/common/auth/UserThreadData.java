package com.kolly.common.auth;

import com.kolly.module.user.po.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kolly
 */
public class UserThreadData {

	private static ThreadLocal<User> user = new ThreadLocal<>();

	private static ThreadLocal<Map<String, Object>> params = new ThreadLocal<>();

	public static Map<String, Object> getParams() {
		if (params.get() == null) {
			params.set(new HashMap<String, Object>());
		}
		return params.get();
	}

	public static void setUser(User u) {
		user.set(u);
	}

	public static User getUser() {
		return user.get();
	}

	public static void removeUser() {
		user.remove();
	}

}
