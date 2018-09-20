package com.spider.util;

public class Boot {
	
	public static void main(String[] args) {
		for(;;){
		//读库读出来的种子链接
		String url="这是一个种子链接";
		
		Fetcher fetcher=new Fetcher();
		Storage storage=new Storage();
		Parser parser=null;//读取数据库的规则
		Spider spider = new Spider();
		spider.fetcher=fetcher;
		spider.parser=parser;
		spider.storage=storage;
		spider.seedUrl=url;
		spider.start();
		}
		
	}
}
