package com.kolly.mainTest;

/**
 * Created by kolly on 2017/10/25.
 */
public class EnumSingletonTest {
	public static void main(String[] args){
		EnumSingleton f = EnumSingleton.INSTANCE;
		f.print();
	}
}
