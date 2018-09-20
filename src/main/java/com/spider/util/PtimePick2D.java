package com.spider.util;

import java.sql.Timestamp;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import com.spider.util.HTTP;
import com.spider.util.TimeT;

public class PtimePick2D {
	private static HTTP http=HTTP.getInstance();
	public static void main(String[] args) throws Exception {
//	ChromeDriver driver =new ChromeDriver();
//	driver.get("file:///C:/Users/Administrator/Desktop/1.html");
//		String text=http.get("http://www.lanjinger.com/news/detail?id=91349").
//				select("body > div.content_wrap > div.content_left > div:nth-child(3) > span:nth-child(2) > span").text();
//		
		//System.out.println(text);
	String text="07时25";//07-18 08:16
		System.out.println(getTime(text));
		
	//System.out.println(timecontent);
	//s="网贷之家 2天前 p2p投资网";
	

	}

	public static String getTime(String timecontent) {
		String regex = null;
		String ptime = null;
		Pattern pattern = null;
		Matcher matcher = null;
		regex = "(\\d{2})[^0-9]{1,5}?(\\d{2})[^0-9-]{1,5}?(\\d{2})[^0-9]{1,5}?(\\d{2})";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {
			ptime=new Timestamp(new Date().getTime()).toString().split("-")[0]+"-"+matcher.group(1)+"-"+matcher.group(2)+" "+matcher.group(3)+":"+matcher.group(4);
			return ptime;
		}
		regex = "(\\d{4})[^0-9]{1,5}?([0-9]{1,2})";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {
			ptime=matcher.group(1)+"-"+matcher.group(2)+"-01";
			return ptime;
		}
		regex = "[^-,月, ]{0,2}(18|17|19)[^0-9]{1,2}?(\\d{1,2})[^0-9]{1,2}?(\\d{1,2})[^0-9]{1,2}?(\\d{1,2})[^0-9]{1,2}?(\\d{1,2})";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return "20"+matcher.group(1)+"-"+matcher.group(2)+"-"+matcher.group(3)+" "+matcher.group(4)+":"+matcher.group(5);
		}
		
		regex = "[^-,月, ]{0,2}(18|17|19)[^0-9, ,日]{1,2}?(\\d{1,2})[^0-9]{1,2}?(\\d{1,2})";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return "20"+matcher.group(1)+"-"+matcher.group(2)+"-"+matcher.group(3);
		}
		if(null==ptime){
			return	ptime = PtimePickHW.getTime(timecontent);
		}
		throw new IllegalStateException(timecontent+"-->date not found");

	}

}
