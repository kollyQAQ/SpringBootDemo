package com.kolly.module.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LolChampionUtils {

	private static Logger logger = LoggerFactory.getLogger(LolChampionUtils.class);

	private static Map<String, String> nameMap = null;

	private static void initialChampions() {

		try {

			nameMap = new HashMap<>();

			InputStream is = LolChampionUtils.class.getClassLoader().getResourceAsStream("lolChampion.csv");
			List<String> lines = IOUtils.readLines(is, "UTF-8");
			for (String line : lines) {
				String[] ss = line.split(",");
				if (ss.length == 3) {
					nameMap.put(ss[2], ss[1]);
				}
			}
		} catch (IOException e) {
			logger.error("Error occurs during loading LOL champion data.", e);
		}
	}

	public static String getCnNameByEnName(String enName) {
		if (nameMap == null) {
			initialChampions();
		}
		String cnName = nameMap.get(enName);
		if (cnName != null) {
			return cnName;
		} else {
			for (String enNameKey : nameMap.keySet()) {
				if (StringUtils.contains(enName, enNameKey)) {
					cnName = nameMap.get(enNameKey);
				}
			}
		}
		return cnName;
	}

	public static void main(String[] args) {
		System.out.println(getCnNameByEnName("LuxDark"));
		System.out.println(getCnNameByEnName("Tristana"));
	}
}

