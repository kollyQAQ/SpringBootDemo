package com.kolly.common.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程监控任务,线程数超过限制告警
 *
 * @author kolly
 */
public class ThreadMonitorJob {

	private static final Logger logger = LoggerFactory.getLogger("access");

	//可通过配置文件配置
	private long maxThreadCount = 1000;
	private int checkCount;

	public void monitorThreadCount() {

		logger.info("ThreadMonitorJob.monitorThreadCount threadCount:{} max:{}", LogInterceptor.getThreadCount(), maxThreadCount);

		if (LogInterceptor.getThreadCount() > maxThreadCount * 0.8 && checkCount++ > 5) {
			checkCount = 0;
			//短信or微信告警
		}
	}

}
