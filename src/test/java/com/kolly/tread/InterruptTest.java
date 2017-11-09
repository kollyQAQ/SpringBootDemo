package com.kolly.tread;

import static java.lang.Thread.sleep;

import java.io.IOException;

/**
 * Created by kolly on 2017/10/30.
 */
public class InterruptTest {
	public static void main(String[] args) throws IOException {
		InterruptTest test = new InterruptTest();
		MyThread thread = test.new MyThread();
		thread.start();
		try {
			sleep(2000);
		} catch (InterruptedException e) {

		}
		thread.interrupt();
	}

	class MyThread extends Thread{
		@Override
		public void run() {
			try {
				System.out.println("进入睡眠状态");
				sleep(10000);
				System.out.println("睡眠完毕");
			} catch (InterruptedException e) {
				System.out.println("得到中断异常");
			}
			System.out.println("run方法执行完毕");
		}
	}
}
