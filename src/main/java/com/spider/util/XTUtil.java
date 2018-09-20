package com.spider.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class XTUtil {
	static long txTime=TimeUnit.MINUTES.toMillis(15);
	static long tmpTime;
	public static void main(String[]args){
		autoXT();
	}
	public static void autoXT(){
		if(tmpTime<System.currentTimeMillis()){
			tmpTime=System.currentTimeMillis()+txTime;
			//写一次心跳。
			try {
				String ip = InetAddress.getLocalHost().getHostAddress();
				Dao.saveCrawlstatus(ip);
			} catch (UnknownHostException e) {
				
			}
			
		}
	}
}
