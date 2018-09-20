package com.spider.util;

import java.util.Queue;

//import com.spider.bean.News;
//单个任务执行过程中，所需要的资源，状态，以及结果。
public class Task implements Runnable{
	
	private static final int MAX_REPTY =3;
	
	 

	//所需资源
	Spider spider;
	String url;
	
	//所有状态
	int repty=0;
	//所有结果
	String  responseContent;
	
	//public News news;
	
	@Override
	public void run() {
		 spider.fetcher.fetch(this);
		 //判断是否下载成功
		 spider.parser.parse(this);
		 spider.parser.linkParse(this);
		 //判断是否能入库
		 spider.storage.store(this);
		
	}
	
//	@Override
//	public void run() {
//		
//		int httpStatus=-1;
//		 try{
//			 //
//			 httpStatus=500;
//			 if(httpStatus!=200){
//				 throw new  IOException ("ERR_CODE:"+httpStatus);
//			 }
//			 
//			 
//			 
//		 }
//		 catch(Exception e){
//			if(e.getMessage().contains("ERR_CODE:500")){
//				if(repty>MAX_REPTY){
//					//写日志后
//					return;
//				}
//				repty++;
//				queue.add(this);
//			}
//			if(e.getMessage().contains("time out")){
//				
//			}
//		 }finally{
//			 
//		 }
//		
//	}

	
}
