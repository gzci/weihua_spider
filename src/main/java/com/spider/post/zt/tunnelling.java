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
import com.spider.util.RegexUtils;
import com.spider.util.ReplaceBlank;
import com.spider.util.TimeT;

import Main.AbstractFiles;

public class tunnelling {
	private static HTTP http=HTTP.getInstance();
	private static Document doc=null;

	public static void main(String[] args) throws Exception{
		run(11);
	}	
	public static void run(int machineId) throws Exception {
		String ref_url0="http://www.tunnelling.cn/PNews/News.aspx";
		String ref_url="https://www.tunnelling.cn/ASHX/PageListAjax.ashx?id=%2FPNews%2FNews.aspx+ctl00%24ctl00%24cphBody%24cphContent%24wcPageList1&source=News_News&conditions=&pageIndex=1";
		String source_name="隧道网";
		String hit_tag="隧道网-资讯";
		String urlcss="figcaption";
		try {
			parser(ref_url0,ref_url,machineId,source_name,urlcss,hit_tag);
			ref_url0="http://www.tunnelling.cn/PSpecial/Special.aspx";
			ref_url="https://www.tunnelling.cn/ASHX/PageListAjax.ashx?id=%2FPSpecial%2FSpecial.aspx+ctl00%24ctl00%24cphBody%24cphContent%24wcPageList1&source=SpecialList&conditions=&pageIndex=1";
			hit_tag="隧道网-专题";
			parser(ref_url0,ref_url,machineId,source_name,urlcss,hit_tag);

			
		} catch (IllegalStateException e) {
			throw new IllegalStateException( tunnelling.class+ "-->"+e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		}
		
	}
	public static void parser(String ref_url0,String ref_url, int machineId,String source_name,String urlcss,String hit_tag) throws IllegalStateException, SQLException {
	
		int category_id = 2;
		doc=http.get(ref_url);
		String regex = null;
		Pattern pattern = null;
		Matcher matcher = null;
		regex = "href=(.*?)\"? title";
		pattern = Pattern.compile(regex);
		
		matcher= pattern.matcher(doc.body().html());
		while(matcher.find()) {
			String contenturl=matcher.group(1).replace("\\\"", "");
			if(!matcher.group(1).replace("\\\"", "").contains("target")){
				contenturl=String.format("https://www.tunnelling.cn/PNews/%s",matcher.group(1).replace("\\\"", "").replace("\\", ""));
				doc=http.get(contenturl);
				String title=doc.select(".detailTitle").text();
				String time=doc.select(".autor i:eq(2)").text();
				Timestamp publish_time=DateUtil2.getTimeStamp(PtimePick.getTime(time));
				if (Dao.checkurl(MainUtil.getMD5(contenturl)) == 1) {
					continue;
				}
				doc=http.get(contenturl);
				Elements body=doc.select(".content");
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
				article.setUrl_type(1);
				article.setFrom_type(2);
				article.setIs_oversea(0);
				// 保存news
				Dao.insertNews(article,machineId);
			}
			
		}
		
	}
	
}
