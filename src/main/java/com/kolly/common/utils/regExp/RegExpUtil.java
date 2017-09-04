package com.kolly.common.utils.regExp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kolly on 2017/9/4.
 */
public class RegExpUtil {

	public static void main(String[] args) {

		String regExp = "^\\d{6}$";
		System.out.println(Pattern.matches(regExp, "123456"));
		System.out.println(Pattern.matches(regExp, "12345678"));

		// 按指定模式在字符串查找
		String line = "This order was placed for QT3000! OK?";
		String pattern = "(\\D*)(\\d+)(.*)";

		// 创建 Pattern 对象
		Pattern r = Pattern.compile(pattern);

		// 现在创建 matcher 对象
		Matcher m = r.matcher(line);
		if (m.find()) {
			System.out.println("Found value: " + m.group(0));
			System.out.println("Found value: " + m.group(1));
			System.out.println("Found value: " + m.group(2));
			System.out.println("Found value: " + m.group(3));
		} else {
			System.out.println("NO MATCH");
		}

		Pattern p = Pattern.compile("\\d+");
		String[] str = p.split("AAA456456VVV0532214DDD");
		for (String s : str) {
			System.out.println(s);
		}
		String[] str2 = Pattern.compile("\\d+").split("AAA456456VVV0532214DDD");
		for (String s : str2) {
			System.out.println(s);
		}
	}
}
