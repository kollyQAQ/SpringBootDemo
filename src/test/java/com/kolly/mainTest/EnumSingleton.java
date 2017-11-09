package com.kolly.mainTest;

public enum EnumSingleton {
	INSTANCE;

	//这个方法可以省略
	public EnumSingleton getInstance() {
		return INSTANCE;
	}

	public void print() {
		System.out.println("hello,world");
	}
}
