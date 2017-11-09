package com.kolly.mainTest;

//懒汉式（静态内部类）
public class LazySingleton {

	private static boolean initialized = false;

	//静态内部类
	private static class SingletonHolder {
		//final为了避免被覆盖
		private static final LazySingleton instance = new LazySingleton();
	}

	private LazySingleton() {
		//防止通过反射实例化对象
		synchronized (SingletonHolder.class) {
			if (!initialized) {
				initialized = true;
			} else {
				throw new RuntimeException("禁止初始化");
			}
		}
	}

	public static LazySingleton getInstance() {
		return SingletonHolder.instance;
	}

}

