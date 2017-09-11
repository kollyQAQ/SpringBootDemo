package com.kolly.module.user.po;

/**
 * Created by kolly on 2017/9/11.
 */
public class User {

	private String uin;
	private String token;

	public User(){}

	public User(String uin, String token) {
		this.uin = uin;
		this.token = token;
	}

	public String getUin() {
		return uin;
	}

	public void setUin(String uin) {
		this.uin = uin;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
