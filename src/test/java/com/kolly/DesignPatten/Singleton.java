package com.kolly.DesignPatten;

/**
 * Created by kolly on 2017/11/9.
 */
public class Singleton {

	private Singleton() {
	}

	public static class HolderClass {
		private static final Singleton INSTANCE = new Singleton();
	}

	public static Singleton getInstance() {
		return HolderClass.INSTANCE;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			new Thread(() -> System.out.println(Singleton.getInstance()))
					.start();
		}
	}
}
