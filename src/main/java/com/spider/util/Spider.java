package com.spider.util;

import java.util.Queue;
//一个大任务，与一个种子对应，包含解析规则。
public class Spider {
	Fetcher fetcher;
	Parser parser;
	Storage storage;
	String seedUrl = "http://www.baidu.com";
	Queue<Task> taskQueue;

	public void start() {
		
	
		
		Double dou;
		//初始化
		taskQueue.add(new Task());
		
		//		parser.setTitleCss("title","text");
		//		parser.setItemLink("#data_list li a");
		
		for (;;) {
			Task task = taskQueue.poll();
			if (null == task) {
				break;
			}
			task.run();
		}
		
	}
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}
