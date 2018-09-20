package com.spider.main;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

@Component
public class Boot implements Runnable {
	@Autowired
	private ApplicationArguments arguments;
	@Autowired
	private  com.spider.main.root root;
	@Autowired
	private  com.spider.main.proot proot;
	@Autowired
	private  com.spider.main.prootzt prootzt;
	@Autowired
	private com.spider.main.wx wx;
	@Autowired
	private com.spider.ci.rootprice rootprice;
	@Autowired
	private com.spider.sendEmail.send send;
	@Autowired
	private com.spider.ci.okcisold okcisold;
	@Override
	public void run() {
		List<String> typeList = arguments.getOptionValues("type");
		String type = typeList.stream().findFirst().orElse(null);
		
		
		switch (type) {
		case "root":
			root.run();
			break;
		case "proot":
			proot.run();
			break;
		case "prootzt":
			prootzt.run();
			break;
		case "wx":
			wx.run();
			break;
		case "rootprice":
			rootprice.run();
			break;
		case "send":
			send.run();
			break;
		case "okcisold":
			okcisold.run();
			break;
		default:
			System.out.println("参数错误");
			break;
		}
	}

}
