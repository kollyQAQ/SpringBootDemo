package com.kolly.common.auth;


import com.kolly.common.interceptor.TimerLogger;

/**
 * @author kolly
 */
public class AppThreadData {

	private static ThreadLocal<TimerLogger> tl = new ThreadLocal<>();

	private static ThreadLocal<Long> threadStartTime = new ThreadLocal<>();

	public static TimerLogger getTimerLogger() {
		if (tl.get() == null) {
			TimerLogger timerLogger = new TimerLogger();
			tl.set(timerLogger);
		};
		return tl.get();
	}

	public static Long getThreadStartTime() {
		return threadStartTime.get();
	}

	public static void setthreadStartTime(long time) {
		threadStartTime.set(time);
	}
}
