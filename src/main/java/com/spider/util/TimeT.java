package com.spider.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeT {

	public static void main(String[] args) {
		// 3 小时前
		// 19 分钟前
		// 5 小时前
//		System.out.println(TimeT.getTimeW(1));
//		System.out.println(TimeT.getTimeD(2));
//		System.out.println(getCurrentTimeH(18));
		System.out.println(getTimeD(0));
		System.out.println(getTimeY(1));
	}
	public static String getCurrentTimeS(int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar beforeTime = Calendar.getInstance();
		beforeTime.add(Calendar.SECOND, -n);// 3秒之前的时间
		Date beforeD = beforeTime.getTime();
		String time = sdf.format(beforeD);
		return time;
	}
	public static String getCurrentTimeM(int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar beforeTime = Calendar.getInstance();
		beforeTime.add(Calendar.MINUTE, -n);// 3分钟之前的时间
		Date beforeD = beforeTime.getTime();
		String time = sdf.format(beforeD);
		return time;
	}

	public static String getCurrentTimeH(int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar beforeTime = Calendar.getInstance();
		beforeTime.add(Calendar.HOUR, -n);// 3小时之前的时间
		Date beforeD = beforeTime.getTime();
		String time = sdf.format(beforeD);
		return time;
	}

	public static String getTimeD(int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar beforeTime = Calendar.getInstance();
		beforeTime.add(Calendar.DATE, -n);// 3天之前的时间
		Date beforeD = beforeTime.getTime();
		String time = sdf.format(beforeD);
		return time;
	}
	public static String getTimeW(int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar beforeTime = Calendar.getInstance();
		beforeTime.add(Calendar.WEDNESDAY, -n);// 3周之前的时间
		Date beforeD = beforeTime.getTime();
		String time = sdf.format(beforeD);
		return time;
	}
	public static String getTimeM(int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar beforeTime = Calendar.getInstance();
		beforeTime.add(Calendar.MONTH, -n);// 3月之前的时间
		Date beforeD = beforeTime.getTime();
		String time = sdf.format(beforeD);
		return time;
	}
	public static String getTimeY(int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar beforeTime = Calendar.getInstance();
		beforeTime.add(Calendar.YEAR, -n);// 3年之前的时间
		Date beforeD = beforeTime.getTime();
		String time = sdf.format(beforeD);
		return time;
	}
}
