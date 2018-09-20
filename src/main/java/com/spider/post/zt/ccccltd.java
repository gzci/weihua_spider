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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.spider.bean.Article;
import com.spider.bean.Seed;
import com.spider.main.Content;
import com.spider.main.root;
import com.spider.util.ArticleGetNoss;
import com.spider.util.Dao;
import com.spider.util.DateUtil2;
import com.spider.util.HTTP;
import com.spider.util.MainUtil;
import com.spider.util.PtimePick;
import com.spider.util.RegexUtils;
import com.spider.util.ReplaceBlank;
import com.spider.util.TimeT;

import Main.AbstractFiles;

public class ccccltd {
	private static HTTP http=HTTP.getInstance();
	private static Document doc=null;
	private static final Logger log = LoggerFactory.getLogger(ccccltd.class);
	public static void main(String[] args) throws Exception{
		run(11);
	}	
	public static void run(int machineId) throws Exception {
		String ref_url0="http://www.ccccltd.cn/news/jcxw/";
		String ref_url="http://www.ccccltd.cn/news/jcxw/jx/index.html";
		String source_name="中国交建";
		String hit_tag="中国交建-简讯";
		String urlcss="li";
		try {
			parser(ref_url0,ref_url,machineId,source_name,urlcss,hit_tag);
			
			ref_url="http://www.ccccltd.cn/news/jcxw/zhxw/index.html";
			hit_tag="中国交建-综合新闻";
			parser(ref_url0,ref_url,machineId,source_name,urlcss,hit_tag);
			
			ref_url="http://www.ccccltd.cn/news/jcxw/sdbd/index.html";
			hit_tag="中国交建-深度报道";
			parser(ref_url0,ref_url,machineId,source_name,urlcss,hit_tag);
			
			ref_url="http://www.ccccltd.cn/news/jcxw/gd/index.html";
			hit_tag="中国交建-观点";
			parser(ref_url0,ref_url,machineId,source_name,urlcss,hit_tag);
			
			ref_url="http://www.ccccltd.cn/news/jcxw/rw/index.html";
			hit_tag="中国交建-人文";
			parser(ref_url0,ref_url,machineId,source_name,urlcss,hit_tag);
		} catch (IllegalStateException e) {
			throw new IllegalStateException( ccccltd.class+ "-->"+e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
	public static void parser(String ref_url0,String ref_url, int machineId,String source_name,String urlcss,String hit_tag) throws IllegalStateException, SQLException {
	
		int category_id = 2;
		doc=http.get(ref_url);
		Elements es=doc.select(urlcss);
		for(int i=0 ;i<es.size();i++){
			try{
				String title=es.get(i).select("a").attr("title");
				String contenturl=es.get(i).select("a").attr("abs:href");
				String time=es.get(i).select("span").text();
				Timestamp publish_time=DateUtil2.getTimeStamp(PtimePick.getTime(time));
				if (Dao.checkurl(MainUtil.getMD5(contenturl)) == 1) {
					continue;
				}
				doc=http.get(contenturl);
				Elements body=doc.select(".TRS_Editor,.scy_xwxqlf_btt");
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
				article.setUrl_type(2);
				article.setFrom_type(2);
				article.setIs_oversea(0);
				// 保存news
				Dao.insertNews(article,machineId);
			}catch(Exception e){
				
			}
			
		}
	}
	
}
