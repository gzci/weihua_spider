package com.spider.sendEmail;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections4.map.LinkedMap;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.spider.util.Dao;
import com.spider.util.HTTP;
import com.spider.util.TimeT;
@Component
public class send implements Runnable{
	private static HTTP http =HTTP.getInstance();
	private static Document doc=null;
	private static Map<String,String> map=new LinkedMap<String,String>();
	static{
		map.put("科技","`st-bigdatabase`.article_self_crawler");
		map.put("科技-微信","`st-bigdatabase`.article_self_crawler");
		map.put("中铁","`zt-bigdatabase`.article_self_crawler");
		map.put("中铁-微信","`zt-bigdatabase`.article_self_crawler");
		map.put("光伏资讯","`ci-bigdatabase`.article_self_crawler");
		map.put("光伏资讯-微信","`ci-bigdatabase`.article_self_crawler");
		map.put("光伏发改委备案","`ci`.article_self_crawler");
		map.put("光伏拟在建","`ci`.tab_rcc_project");
		map.put("光伏备案","`ci`.tab_put_on_record");
		map.put("光伏招中标","`ci`.tab_raw_bidd");
		
	}
	@Override
	public void run() {
		String sendstr="";
		for (Map.Entry<String, String> entry : map.entrySet()) { 
			  String type=entry.getKey();
			  String tablecheck=entry.getValue();
			  String is_not="not";
			  String date="";
			  date=TimeT.getTimeD(1);
			  sendbean o=new sendbean();
			  o.setType(type);
			  o.setTablecheck(tablecheck);
			  o.setDate(date);
			  if(type.contains("微信")){
				  is_not="";
				  o.setIs_not(is_not);;
				  o=Dao.checkNum(o);
			  }else{
				  o.setIs_not(is_not);
				  o=Dao.checkNum(o);
			  }
			  sendstr+=o+"\r";
			}
		Map<String,String>data=new HashMap<String,String>();
		data.put("destEmails", "wwharbs@163.com,liuxiulei@bistu.edu.cn,litengyang@bluetidedata.com,zhangyingying@bluetidedata.com"
				+ ",daiqiu@bluetidedata.com,guozhihua@bluetidedata.com");
		data.put("msg", sendstr.replace("`", ""));
		data.put("subject", "数据统计");
		data.put("sender", "王伟华");
	doc=http.post("http://tools.yansou.space:8082/mail/sendEmail",data);
//		data.clear();
//		data.put("destEmails", "liuxiulei@bistu.edu.cn");
//		data.put("msg", sendstr);
//		data.put("subject", "数据统计");
//		data.put("sender", "王伟华");
//		System.out.println(doc);
	//doc=http.post("http://tools.yansou.space:8082/mail/sendEmail",data);
//	System.out.println(doc);
	
	}

}
