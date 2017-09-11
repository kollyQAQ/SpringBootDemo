package com.kolly.common.interceptor;

import com.kolly.common.BasicResult;
import com.kolly.common.auth.LoginAuthCheck;
import com.kolly.common.auth.UserThreadData;
import com.kolly.common.web.BaseWebService;
import com.kolly.module.user.biz.UserBiz;
import com.kolly.module.user.po.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登录鉴权
 *
 * @author kolly
 */
public class LoginAuthInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger("access");

	@Autowired
	private UserBiz userBiz;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// 用户登录鉴权判断
		if (handler instanceof HandlerMethod) {

			// 获取鉴权设置
			HandlerMethod method = (HandlerMethod) handler;
			LoginAuthCheck authAnnotation = method.getMethodAnnotation(LoginAuthCheck.class);

			if (authAnnotation != null && authAnnotation.authRequired()) {

				String uin = BaseWebService.getValueFromHeaderOrCookie(request, "uin");
				String token = BaseWebService.getValueFromHeaderOrCookie(request, "token");

				if (userBiz.checkLogin(uin, token)) {

					logger.info("AuthInterceptor checkLogin success. URI:{} uin:{} token:{}", request.getRequestURI(), uin, token);

					UserThreadData.setUser(new User(uin, token));

				} else {
					BaseWebService.setCookie(response, "userId", "", 0);
					BaseWebService.setCookie(response, "userToken", "", 0);
					BaseWebService.responseJson(response, BasicResult.createFailResult(-999, "登录鉴权失败"), 200);

					logger.warn("AuthInterceptor checkLogin fail. URI:{} uin:{} token:{}", request.getRequestURI(), uin, token);

					return false;
				}
			}
		}

		return true;

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		UserThreadData.removeUser();
	}

}
