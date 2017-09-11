package com.kolly.common.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用户登录鉴权注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginAuthCheck {

	/**
	 * 是否需要用户鉴权
	 */
	public boolean authRequired() default false;

	/**
	 * 是否需要请求头参数版本控制
	 */
	public boolean versionControl() default true;
}