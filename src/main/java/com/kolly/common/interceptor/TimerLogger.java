package com.kolly.common.interceptor;

import java.util.ArrayList;
import java.util.List;

/**
 * 记录一个线程各个过程的耗时
 *
 * @author kolly
 */
public class TimerLogger {

	private long start = System.currentTimeMillis();
	private long end = start;

	private List<Long> timer = new ArrayList<>();

	public TimerLogger() {

	}

	public TimerLogger mark() {
		timer.add(System.currentTimeMillis() - end);
		end = System.currentTimeMillis();
		return this;
	}

	public boolean isMarked() {
		return !timer.isEmpty();
	}

	public long getTotal() {
		return end - start;
	}

	public TimerLogger reset() {
		start = System.currentTimeMillis();
		end = start;
		timer.clear();
		return this;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Cost:" + getTotal());
		for (int i = 0; i < timer.size(); i++) {
			sb.append(" T").append(i).append(":").append(timer.get(i));
		}
		return sb.toString();
	}

	public static void main(String[] args) throws InterruptedException {

		TimerLogger s = new TimerLogger();

		Thread.sleep(1000);
		s.mark();
		Thread.sleep(800);
		s.mark();
		Thread.sleep(500);

		System.out.println(s.mark().toString());

	}

}
