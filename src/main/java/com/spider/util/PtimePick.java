package com.spider.util;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Element;

import com.spider.util.HTTP;
import com.spider.util.TimeT;

public class PtimePick {
	private static HTTP http=HTTP.getInstance();
	public static void main(String[] args) throws Exception {
//	ChromeDriver driver =new ChromeDriver();
	//driver.get("file:///C:/Users/Administrator/Desktop/1.html");
		//String text=http.get("https://www.infrastructureusa.org/chairman-shusters-vision-statement-infrastructure-discussion-draft/").
			//	selectFirst("h2").text();
		//System.out.println(http.get("https://www.infrastructureusa.org/chairman-shusters-vision-statement-infrastructure-discussion-draft/"));
		//System.out.println(text);
		System.out.println(	getTime("10 秒 ,前"));//views-field-title
//	for(Element e:http.get("https://www.theengineer.co.uk/").
//			select(".block-lead .hentry-date")){
//		try{
//			System.out.println(e.text());
//			System.err.println(PtimePick.getTime(e.text()));
//			
//		}catch(Exception e0){
//			System.out.println(e.attr("href"));
//			System.out.println(PtimePick.getTime(e.attr("href")));
//		}
//	}
	
	}

	public static String getTime(String timecontent) {
	//	System.out.println(timecontent);
		String regex = null;
		String ptime = null;
		Pattern pattern = null;
		Matcher matcher = null;
		Map<String,String> date=new HashMap<String,String>();
    	date.put("jan", "1");
    	date.put("january", "1");
    	date.put("feb", "2");
    	date.put("february", "2");
    	date.put("mar", "3");
    	date.put("march", "3");
    	date.put("apr", "4");
    	date.put("april", "4");
    	date.put("may", "5");
    	date.put("jun", "6");
    	date.put("june", "6");
    	date.put("jul", "7");
    	date.put("july", "7");
    	date.put("aug", "8");
    	date.put("august", "8");
    	date.put("sep", "9");
    	date.put("september", "9");
    	date.put("oct", "10");
    	date.put("october", "10");
    	date.put("nov", "11");
    	date.put("november", "11");
    	date.put("dec", "12");
    	date.put("december", "12");
    	timecontent=timecontent.toLowerCase();
    	
		regex = "((jan)|(feb)|(mar)|(apr)|(may)|(jun)|(jul)|(aug)|(sep)|(oct)|(nov)|(dec)|(january)|(february)|(march)|(april)|(june)|(july)|(august)|(september)|(october)|(november)|(december))[^0-9]{0,5}?(\\d{1,2})[^0-9]{0,5}?(\\d{4})";
		pattern = Pattern.compile(regex);
    	
		if ((matcher = pattern.matcher(timecontent)).find()) {
			ptime=matcher.group(matcher.groupCount())+"-"+matcher.group(1)+"-"+matcher.group(matcher.groupCount()-1);
	    	
	    	ptime=ptime.replace(ptime.split("-")[1],date.get(ptime.split("-")[1]));
	    	return ptime;
		}
		regex = "(\\d{1,2})[^0-9]{0,5}?((jan)|(feb)|(mar)|(apr)|(may)|(jun)|(jul)|(aug)|(sep)|(oct)|(nov)|(dec)|(january)|(february)|(march)|(april)|(june)|(july)|(august)|(september)|(october)|(november)|(december))[^0-9]{0,5}?(\\d{4})";
		pattern = Pattern.compile(regex);
    	
		if ((matcher = pattern.matcher(timecontent)).find()) {
			ptime=matcher.group(matcher.groupCount())+"-"+matcher.group(2)+"-"+matcher.group(1);
	    	
	    	ptime=ptime.replace(ptime.split("-")[1],date.get(ptime.split("-")[1]));
	    	return ptime;
		}
		regex = "(\\d{1,2})[^0-9]{0,5}?((jan)|(feb)|(mar)|(apr)|(may)|(jun)|(jul)|(aug)|(sep)|(oct)|(nov)|(dec)|(january)|(february)|(march)|(april)|(june)|(july)|(august)|(september)|(october)|(november)|(december))";
		pattern = Pattern.compile(regex);
    	
		if ((matcher = pattern.matcher(timecontent)).find()) {
			ptime=new Timestamp(new Date().getTime()).toString().split("-")[0]+"-"+matcher.group(2)+"-"+matcher.group(1);
	    	
	    	ptime=ptime.replace(ptime.split("-")[1],date.get(ptime.split("-")[1]));
	    	return ptime;
		}
		regex = "((jan)|(feb)|(mar)|(apr)|(may)|(jun)|(jul)|(aug)|(sep)|(oct)|(nov)|(dec)|(january)|(february)|(march)|(april)|(june)|(july)|(august)|(september)|(october)|(november)|(december))[^0-9]{0,5}?(\\d{4})";
		pattern = Pattern.compile(regex);
    	
		if ((matcher = pattern.matcher(timecontent)).find()) {
			ptime=matcher.group(matcher.groupCount())+"-"+matcher.group(1)+"-01";
	    	
	    	ptime=ptime.replace(ptime.split("-")[1],date.get(ptime.split("-")[1]));
	    	return ptime;
		}
		regex = "(\\d{4})[^0-9]{1,2}?(\\d{1,2})[^0-9]{1,2}?(\\d{1,2})[^0-9]{1,2}?(\\d{1,2})[^0-9]{1,2}?(\\d{1,2})";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return matcher.group(1)+"-"+matcher.group(2)+"-"+matcher.group(3)+" "+matcher.group(4)+":"+matcher.group(5);
		}
		
		regex = "(\\d{4})[^0-9]{1,2}?(\\d{1,2})[^0-9]{1,2}?(\\d{1,2})";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return matcher.group(1)+"-"+matcher.group(2)+"-"+matcher.group(3);
		}
		//////////////////////////////////////////////////////////
		

//		regex = "([0-9]{1,2})[^0-9]{1,5}?([0-9]{1,2})";
//		pattern = Pattern.compile(regex);
//		if ((matcher = pattern.matcher(timecontent)).find()) {
//
//			if (matcher.group().contains("月") || matcher.group().contains("-")) {
//				ptime = new Timestamp(new Date().getTime()).toString().split("-")[0] + "-" + matcher.group(1) + "-"
//						+ matcher.group(2);
//
//			} else if (matcher.group().contains(":")) {
//				ptime = new Timestamp(new Date().getTime()).toString().split(" ")[0] + " " + matcher.group(1) + ":"
//						+ matcher.group(2);
//
//			}
//			return ptime;
//		}
		
		//////////////////////////////////////////////////////////////
		
		regex = "昨天";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {
			return TimeT.getTimeD(1);
		}
		regex = "前天";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return TimeT.getTimeD(2);
		}
		regex = "([0-9]{1,2})\\s*秒";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return TimeT.getCurrentTimeS(Integer.valueOf(matcher.group(1)));
		}
		regex = "([0-9]{1,2})\\s*分钟\\s*前";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return TimeT.getCurrentTimeM(Integer.valueOf(matcher.group(1)));
		}
		
		regex = "([0-9]{1,2})\\s*小时\\s*前";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return TimeT.getCurrentTimeH(Integer.valueOf(matcher.group(1)));
		}
		regex = "([0-9]{1,2})\\s*天\\s*前";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return TimeT.getTimeD(Integer.valueOf(matcher.group(1)));
		}
		regex = "([0-9]{1,2})\\s*周\\s*前";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return TimeT.getTimeW(Integer.valueOf(matcher.group(1)));
		}
		regex = "([0-9]{1,2})\\s*月\\s*前";
		pattern = Pattern.compile(regex);
		if ((matcher = pattern.matcher(timecontent)).find()) {

			return TimeT.getTimeM(Integer.valueOf(matcher.group(1)));
		}
		if(null==ptime){
			return ptime = PtimePick2D.getTime(timecontent);
		}
		
		throw new IllegalStateException(timecontent+"-->date not found");

	}

}
