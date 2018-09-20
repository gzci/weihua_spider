package com.spider.post.zt;


import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spider.bean.Article;
import com.spider.bean.Seed;
import com.spider.main.Content;
import com.spider.util.ArticleGetNoss;
import com.spider.util.Dao;
import com.spider.util.DateUtil2;
import com.spider.util.HTTP;
import com.spider.util.JsonPathSelector;
import com.spider.util.MainUtil;
import com.spider.util.PtimePick;
import com.spider.util.RegexUtils;
import com.spider.util.ReplaceBlank;
import com.spider.util.TimeT;

import Main.AbstractFiles;

public class chinaeda {
	private static HTTP http=HTTP.getInstance();
	private static Document doc=null;

	public static void main(String[] args) throws Exception{
		run(11);
	}	
	public static void run(int machineId) throws Exception {
		String ref_url0="http://www.chinaeda.org/HYdynamics.aspx?Mark=gn";
		String ref_url="http://www.chinaeda.org/Hander/Handler.ashx?p=1&orderby=&xuan=1";
		String source_name="中国勘察设计协会";
		String hit_tag="中国勘察设计协会-国内动态";
		String urlcss="figcaption";
		try {
			parser(ref_url0,ref_url,machineId,source_name,urlcss,hit_tag);
			ref_url0="http://www.chinaeda.org/HYdynamics.aspx?Mark=gj";
			ref_url="http://www.chinaeda.org/Hander/Handler.ashx?p=1&orderby=&xuan=2";
			hit_tag="中国勘察设计协会-国际动态";
			parser(ref_url0,ref_url,machineId,source_name,urlcss,hit_tag);

			
		} catch (IllegalStateException e) {
			throw new IllegalStateException( chinaeda.class+ "-->"+e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
	public static void parser(String ref_url0,String ref_url, int machineId,String source_name,String urlcss,String hit_tag) throws IllegalStateException, SQLException {
	
		int category_id = 2;
		doc=http.get(ref_url);
		List<String> listID= new JsonPathSelector("$..ID").selectList(doc.body().html());
		List<String> listTypeID= new JsonPathSelector("$..TypeID").selectList(doc.body().html());
		List<String> listTitle= new JsonPathSelector("$..Title").selectList(doc.body().html());
		List<String> listsubdate= new JsonPathSelector("$..subdate").selectList(doc.body().html());
		for(int i=0;i<listID.size();i++){
			String contenturl=String.format("http://www.chinaeda.org/HYdynamics.aspx?id=%s&typeid=%s", listID.get(i),listTypeID.get(i));
			String title=listTitle.get(i);
			String time=listsubdate.get(i);
			Timestamp publish_time=DateUtil2.getTimeStamp(PtimePick.getTime(time));
			if (Dao.checkurl(MainUtil.getMD5(contenturl)) == 1) {
				continue;
			}
			doc=http.get(contenturl);
			Elements body=doc.select(".subpage_main tbody td");
			body=ArticleGetNoss.getArticleContent(body,contenturl);
			String brief=null;
			 brief = AbstractFiles.getAbstractFromStr(body.text(), (float) 0.1);
			 if (brief.contains("本文不适合做摘要处理")) {
				brief="";
			 }
			Article article = new Article();
			article.setSource_name(ReplaceBlank.remove(source_name));
			article.setRef_url(ref_url0);
			article.setContent(body.outerHtml());
			article.setUrl(contenturl);
			article.setMd5_url(MainUtil.getMD5(contenturl));
			article.setTitle(title);
			article.setPublish_time(publish_time);
			article.setCategory_id(category_id);
			
			article.setHit_tag(hit_tag);
			article.setBrief(brief);
			article.setHtml_source(doc.html());
			// 自爬虫
			article.setUrl_type(3);
			article.setFrom_type(2);
			article.setIs_oversea(0);
			// 保存news
			Dao.insertNews(article,machineId);
		}
	}
	
}
