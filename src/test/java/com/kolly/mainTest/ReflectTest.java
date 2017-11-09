package com.kolly.mainTest;

import java.lang.reflect.Constructor;

public class ReflectTest {

	public static void main(String[] args) throws Exception {

		//正常获取单例
		LazySingleton s1 = LazySingleton.getInstance();

		//利用反射机制，来调用私有构造方法创建一个实例
		Constructor<LazySingleton> constructor = LazySingleton.class.getDeclaredConstructor();
		//强制授权让他可以访问
		constructor.setAccessible(true);

		LazySingleton s2 = constructor.newInstance();

		System.out.println(s1 == s2);
	}

}
