package com.spider.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

	public static void main(String[] args) {
	String s="'2018-654004-41-03-014103','654004-FG-BA-01','01','104564','20180815112811903N'";
	System.out.println(regex("tanchu\\('(.*?)','(.*?)','(.*?)','(.*?)','(.*?)'\\)",s,5));
	}

	public static String regex(String rex, String str, int n) {

		Pattern pattern = Pattern.compile(rex);
		Matcher matcher = pattern.matcher(str);

		if (matcher.find()) {

			return matcher.group(n);
		}
		return null;

	}

}
