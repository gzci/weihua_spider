package com.spider.main;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import com.spider.post.kr;
import com.spider.post.krzx;

import com.spider.post.readhub;

import com.spider.util.Dao;
@Component
public class proot implements Runnable{
	private static final Logger log = LoggerFactory.getLogger(proot.class);
	private static long KEY = 0;
	
	@Override
	public void run() {
		int machineId=11;
		String ip=null;
		List<String> listip=new ArrayList<String>();
		listip.add("192.168.5.96");
		listip.add("192.168.5.97");
		listip.add("192.168.5.98");
		try {
			
			ip = InetAddress.getLocalHost().getHostAddress();
			if(!listip.contains(ip)){
				System.out.println("代码部署错误");
				return;
			}
			if(Dao.checkPostIp(ip)==1){
				return;
			}
			//krzx.run(machineId);
			kr.run(machineId);
			readhub.run(machineId);
			Dao.updatePostIp(ip);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	
	
		
		
	}

}
