package com.kolly;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * Created by kolly on 2017/8/24.
 */
public class MainTest {

	public static void main(String[] args) {

		List<String> list = Arrays.asList("java", "c", "php");

		list.removeIf(p -> p.equals("java"));

		list.removeIf(new Predicate<String>() {
			@Override
			public boolean test(String lang) {
				return lang.equals("php");
			}
		});

		for (String lang : list) {
			System.out.println(lang);
		}
	}

}
