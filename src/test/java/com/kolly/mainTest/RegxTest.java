package com.kolly.mainTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kolly on 2017/9/7.
 */
public class RegxTest {

	public static void main(String args[]) {

		// String to be scanned to find the pattern.
		String line = "This order was placed for QT3000! OK?";
		String pattern = "(\\D+)(\\d+)(\\D+)";

		// Create a Pattern object
		Pattern r = Pattern.compile(pattern);

		// Now create matcher object.
		Matcher m = r.matcher(line);
		while (m.matches()) {
			System.out.println("find");
			System.out.println("Found value: " + m.group(0));
			System.out.println("Found value: " + m.group(1));
			System.out.println("Found value: " + m.group(2));
			System.out.println("Found value: " + m.group(3));
		}

		System.out.println("NO MATCH");

	}
}
