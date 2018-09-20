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
import com.spider.util.MainUtil;
import com.spider.util.PtimePick;
import com.spider.util.ReplaceBlank;
import com.spider.util.TimeT;

import Main.AbstractFiles;

public class inhabitat {
	private static HTTP http=HTTP.getInstance();
	private static Document doc=null;
	private static Response res;
	public static void main(String[] args) throws IOException{
		run(11);
	}	
	public static void run(int machineId) throws IOException {
		String ref_url="https://inhabitat.com/news/";
		String source_name="inhabitat";
		String hit_tag="inhabitat-news";
		String urlcss="#posts .archive";
		try {
			parser(ref_url,machineId,source_name,urlcss,hit_tag);
			ref_url="https://inhabitat.com/environment/";
			hit_tag="inhabitat-environment";			
			parser(ref_url,machineId,source_name,urlcss,hit_tag);
			ref_url="https://inhabitat.com/architecture/";
			hit_tag="inhabitat-architecture";			
			parser(ref_url,machineId,source_name,urlcss,hit_tag);
			ref_url="https://inhabitat.com/design/";
			hit_tag="inhabitat-design";			
			parser(ref_url,machineId,source_name,urlcss,hit_tag);
			ref_url="https://inhabitat.com/innovation/";
			hit_tag="inhabitat-innovation";			
			parser(ref_url,machineId,source_name,urlcss,hit_tag);
		} catch (IllegalStateException e) {
			throw new IllegalStateException( inhabitat.class+ "-->"+e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		
	}
	public static void parser(String ref_url, int machineId,String source_name,String urlcss,String hit_tag) throws IllegalStateException, SQLException, IOException {
	
	
		int category_id = 2;
		res = Jsoup.connect(ref_url).ignoreContentType(true).method(Method.GET).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36").execute();
		doc=Jsoup.parse(res.bodyStream(), "UTF-8", ref_url);
		Elements es=doc.select(urlcss);
		
		for(int i=0 ;i<es.size();i++){
			try{
				String title=es.get(i).select("h2 a[title]").text();
				String contenturl=es.get(i).select("h2 a[title]").attr("abs:href");
				String time=es.get(i).select(".share ul li.time a").text();
				Timestamp publish_time=DateUtil2.getTimeStamp(PtimePick.getTime(time));
				if (Dao.checkurl(MainUtil.getMD5(contenturl)) == 1) {
					continue;
				}
				res = Jsoup.connect(contenturl).ignoreContentType(true).method(Method.GET).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36").execute();
				doc=Jsoup.parse(res.bodyStream(), "UTF-8", contenturl);
				Elements body=doc.select(".post-content");
				body=ArticleGetNoss.getArticleContent(body,contenturl);
				String brief=null;
				 brief = AbstractFiles.getAbstractFromStr(body.text(), (float) 0.1);
				 if (brief.contains("本文不适合做摘要处理")) {
					brief="";
				 }
				Article article = new Article();
				article.setSource_name(ReplaceBlank.remove(source_name));
				article.setRef_url(ref_url);
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
				article.setIs_oversea(1);
				// 保存news
				Dao.insertNews(article,machineId);
			}catch(Exception e){
				
			}
			
		}
	}
	
}
