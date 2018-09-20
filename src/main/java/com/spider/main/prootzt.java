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
import com.spider.post.zt.ccccltd;
import com.spider.post.zt.chinaeda;
import com.spider.post.zt.crcc;
import com.spider.post.zt.inhabitat;
import com.spider.post.zt.itsinternational;
import com.spider.post.zt.newatlas;
import com.spider.post.zt.tunnelling;
import com.spider.post.zt.tunneltalk;
import com.spider.util.Dao;

@Component
public class prootzt implements Runnable{
	private static final Logger log =LoggerFactory.getLogger(root.class);
	private static long KEY = 0;
	
	@Override
	public void run() {
		int machineId=11;
		String ip=null;
		List<String> listip=new ArrayList<String>();
		listip.add("192.168.5.99");
		listip.add("192.168.5.100");
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
			if(!listip.contains(ip)){
				System.out.println("代码部署错误");
				return;
			}
			if(Dao.checkPostIp(ip)==1){
				return;
			}
			ccccltd.run(machineId);
			crcc.run(machineId);
			inhabitat.run(machineId);
			itsinternational.run(machineId);
			newatlas.run(machineId);
			tunneltalk.run(machineId);
			chinaeda.run(machineId);
			tunnelling.run(machineId);
			Dao.updatePostIp(ip);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
	
	
		
		
	}

}
