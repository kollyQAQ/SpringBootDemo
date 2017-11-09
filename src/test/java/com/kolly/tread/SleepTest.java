package com.kolly.tread;

import java.io.IOException;

/**
 * Created by kolly on 2017/10/30.
 */
public class SleepTest {

	//sleep方法不会释放锁，也就是说如果当前线程持有对某个对象的锁，则即使调用sleep方法，其他线程也无法访问这个对象

	private int i = 10;

	public static void main(String[] args) throws IOException {
		SleepTest test = new SleepTest();
		MyThread thread1 = test.new MyThread();
		MyThread thread2 = test.new MyThread();
		thread1.start();
		thread2.start();
	}


	class MyThread extends Thread {
		@Override
		public void run() {
			synchronized (SleepTest.class) {
				i++;
				System.out.println("i:" + i);
				try {
					System.out.println("线程" + Thread.currentThread().getName() + "进入睡眠状态");
					sleep(3000);
				} catch (InterruptedException e) {
					// TODO: handle exception
				}
				System.out.println("线程" + Thread.currentThread().getName() + "睡眠结束");
				i++;
				System.out.println("i:" + i);
			}
		}
	}
}

