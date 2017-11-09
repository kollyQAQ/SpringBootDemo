package com.kolly.mainTest;

//饿汉式
public class HungrySingleton {

	private static final HungrySingleton instance = new HungrySingleton();

	private HungrySingleton() {}

	public static HungrySingleton getInstance() {
		return instance;
	}

}

