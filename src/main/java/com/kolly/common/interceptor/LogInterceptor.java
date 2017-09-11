package com.kolly.common.interceptor;

import com.kolly.common.auth.AppThreadData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志记录： 1. 请求参数  2. 请求耗时
 *
 * @author kolly
 */

public class LogInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger("access");

	private static Long threadCount = 0L;

	/**
	 * 因为interceptor是单例的,用ThreadLocal类记录线程请求开始时间
	 */
	private ThreadLocal<Long> requestBeginTime = new ThreadLocal<>();

	/**
	 * Log the request entrance
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		incThread();
		AppThreadData.SetthreadStartTime(System.currentTimeMillis());
		AppThreadData.getTimerLogger().reset();

		StringBuilder sb = new StringBuilder();
		for (String key : request.getParameterMap().keySet()) {
			sb.append(key).append(":").append(request.getParameterValues(key)[0]).append(";");
		}
		logger.info("Request URI:{} requestParam:[{}", request.getRequestURI(), sb.toString() + "]");

		return true;
	}

	/**
	 * Log the request exit
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		decrThread();
		long cost = System.currentTimeMillis() - AppThreadData.getThreadStartTime();
		AppThreadData.getTimerLogger().mark();
		if (cost > 999) {
			logger.error("Request finished: Status:{} Busy:{} URI:{} Cost:{} TimerLogger:{}", response.getStatus(), threadCount, request.getRequestURI(), cost, AppThreadData.getTimerLogger());
		} else {
			logger.info("Request finished: Status:{} Busy:{} URI:{} Cost:{}", response.getStatus(), threadCount, request.getRequestURI(), cost);
		}
	}

	private synchronized void incThread() {
		threadCount++;
	}

	private synchronized void decrThread() {
		threadCount--;
	}

	public static long getThreadCount() {
		return threadCount;
	}
}
