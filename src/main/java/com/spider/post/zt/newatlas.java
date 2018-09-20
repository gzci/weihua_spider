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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

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
import com.spider.util.webdriverChrom;

import Main.AbstractFiles;

public class newatlas {
	private static HTTP http=HTTP.getInstance();
	private static Document doc=null;
	private static webdriverChrom web=webdriverChrom.getInstance();
	private static RemoteWebDriver driver=web.get("https://www.baidu.com/");
	public static void main(String[] args) throws Exception{
		run(11);
	}	
	public static void run(int machineId) throws Exception {
		driver = new ChromeDriver();
		String ref_url="https://newatlas.com/";
		String source_name="GIZMAG";
		String hit_tag="GIZMAG";
		String urlcss="a.permalink";
		try {
		
			parser(ref_url,machineId,source_name,urlcss,hit_tag);
			
			
		} catch (IllegalStateException e) {
			throw new IllegalStateException( newatlas.class+ "-->"+e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
	public static void parser(String ref_url, int machineId,String source_name,String urlcss,String hit_tag) throws IllegalStateException, SQLException {
	
	
		int category_id = 2;
		doc=http.get(ref_url);
		Elements es=doc.select(urlcss);
		System.out.println(es.size());
		for(int i=0 ;i<es.size();i++){
			try{
				String title=es.get(i).text();
				String contenturl=es.get(i).attr("abs:href");
				driver.get(contenturl);
				doc=Jsoup.parse(driver.getPageSource());
				String time=doc.select(".article-detail__byline span").text();
				Timestamp publish_time=DateUtil2.getTimeStamp(PtimePick.getTime(time));
				if (Dao.checkurl(MainUtil.getMD5(contenturl)) == 1) {
					continue;
				}
				
				Elements body=doc.select(".ArticleBody");
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
