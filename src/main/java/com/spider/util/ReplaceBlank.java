package com.spider.util;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class ReplaceBlank {

	public static String remove(String str) {
		String dest = "";
		if (str!=null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("").replace("null", "");
		}
		return dest;
	}
	public static void main(String[] args) {
		System.out.println(ReplaceBlank.remove("2017，说说   铁路的那些“小目标”"));
	}
}