package com.spider.util;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.spider.util.HTTP;
import com.spider.util.TimeT;

public class PtimePickHW {
	private static HTTP http=HTTP.getInstance();
	
    public static void main(String[] args) {
    	String timecontent = "30 min ago";
    	System.out.println(getTime(timecontent));
    }
    public static String getTime(String timecontent) {
    	
    	timecontent=timecontent.toLowerCase();
		String regex = null;
		String ptime = null;
		Pattern pattern = null;
		Matcher matcher = null;
		regex = "([0-9]{1,2})\\s*week";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return TimeT.getTimeW(Integer.valueOf(matcher.group(1)));
		}
		regex = "([0-9]{1,2})\\s*second";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return TimeT.getCurrentTimeS(Integer.valueOf(matcher.group(1)));
		}
		regex = "([0-9]{1,2})\\s*month";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return TimeT.getTimeM(Integer.valueOf(matcher.group(1)));
		}
		regex = "([0-9]{1,2})\\s*min";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return TimeT.getCurrentTimeM(Integer.valueOf(matcher.group(1)));
		}
		regex = "([0-9]{1,2})\\s*day";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return TimeT.getTimeD(Integer.valueOf(matcher.group(1)));
		}//Yesterday
		regex = "([0-9]{1,2})\\s*hour";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return TimeT.getCurrentTimeH(Integer.valueOf(matcher.group(1)));
		}
		regex = "yesterday";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return TimeT.getTimeY(1);
		}
		
		//9/7/18
//		regex = "(\\d{2})/(\\d{2})/(\\d{4})";
//		pattern = Pattern.compile(regex);
//		if ((matcher = pattern.matcher(timecontent)).find()) {
//
//			return matcher.group(3)+"-"+matcher.group(2)+"-"+matcher.group(1);
//		}
		regex = "(\\d{1,2})/(\\d{1,2})/(18|17|19)";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return "20"+matcher.group(3)+"-"+matcher.group(1)+"-"+matcher.group(2);
		}
		regex = "(\\d{2})[^0-9]{0,5}?(\\d{2})[^0-9]{0,5}?(\\d{4})";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return matcher.group(3)+"-"+matcher.group(1)+"-"+matcher.group(2);
		}
		regex = "(\\d{2})[^0-9:时 ]{1,5}?(\\d{2})";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return new Timestamp(new Date().getTime()).toString().split("-")[0]+"-"+matcher.group(1)+"-"+matcher.group(2);
		}
		regex = "(\\d{2})[^0-9-]{1,5}?(\\d{2})";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return new Timestamp(new Date().getTime()).toString().split(" ")[0]+" "+matcher.group(1)+":"+matcher.group(2);
		}
		throw new IllegalStateException(timecontent+"-->date not found");

	}

}