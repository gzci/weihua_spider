package com.spider.util;

import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Parser {
	String itemLinkCssQuery;
	String titleCssQuery;
	String timeCssQuery;
	String contentCssQuery;
	
	
	
	public void parse(Task task){
		Document dom = Jsoup.parse(task.responseContent,task.url);
		//task.news.setTitle(dom.select(titleCssQuery).text());
		
		
		
	}
	public void linkParse(Task task){
		Document dom=Jsoup.parse(task.responseContent,task.url);
		dom.select(itemLinkCssQuery);
		for(Element item:dom.select(itemLinkCssQuery)
				){
			String href=item.attr("href");
			//href需要补全。
			
		//创建一个新的Task并入队列
		Task newItemTask=new Task();
		newItemTask.url=href;
		newItemTask.spider=task.spider;
		String url;
		//...除了URL字段，其他全部复制过去
		task.spider.taskQueue.add(newItemTask);
	}}
}
