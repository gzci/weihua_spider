package com.spider.post.ci;


import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import com.spider.util.JsonPathSelector;
import com.spider.util.MainUtil;
import com.spider.util.PtimePick;
import com.spider.util.RegexUtils;
import com.spider.util.ReplaceBlank;
import com.spider.util.TimeT;

import Main.AbstractFiles;

public class fujian {
	private static HTTP http=HTTP.getInstance();
	private static Document doc=null;
	private static final Logger log = LoggerFactory.getLogger(fujian.class);
	public static void main(String[] args) throws Exception{
		run(11);
	}	
	public static void run(int machineId) throws Exception {
		String ref_url0="http://fgw.fujian.gov.cn/zfxxgkzl/zfxxgkml/";
		String ref_url="http://fgw.fujian.gov.cn/was5/web/search?channelid=229105&templet=docs.jsp&sortfield=-pubdate&classsql=modal%3D1*docpuburl%3D%27%25http%3A%2F%2Ffgw.fujian.gov.cn%2Fzfxxgkzl%2Fzfxxgkml%2F%25%27&prepage=7&page=1";
		String source_name="福建省发改委";
		String hit_tag="福建省发改委-政府信息公开专栏 - 政府信息公开目录";
		
		try {
			parser(ref_url0,ref_url,machineId,source_name,hit_tag);
			ref_url0="http://fgw.fujian.gov.cn/xxgk/xzxk/";
			ref_url="http://fgw.fujian.gov.cn/was5/web/search?channelid=229105&templet=advsch.jsp&sortfield=-docorderpri%2C-docreltime&classsql=chnlid%3D9403&prepage=33&page=1";
			
			hit_tag="福建省发改委-信息公开 -行政许可";
			parser(ref_url0,ref_url,machineId,source_name,hit_tag);
		} catch (IllegalStateException e) {
			throw new IllegalStateException( fujian.class+ "-->"+e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
	public static void parser(String ref_url0,String ref_url, int machineId,String source_name,String hit_tag) throws IllegalStateException, SQLException {

		
		
		int category_id = 0;
		JSONObject data = new JSONObject();
		doc=http.get(ref_url);
		String responsestr=doc.body().html();
		String urljson="$..url";
		String titlejson="$..title";
		String ptimejson="$..pubtime";
		
		
		List<String> listurl= new JsonPathSelector(urljson).selectList(responsestr);
		List<String> listtitle= new JsonPathSelector(titlejson).selectList(responsestr);
		List<String> listtime= new JsonPathSelector(ptimejson).selectList(responsestr);
		for(int i=0 ;i<listurl.size();i++){
			String title=listtitle.get(i);
			String contenturl=listurl.get(i);
			String timestr=PtimePick.getTime(listtime.get(i));
			Timestamp publish_time=DateUtil2.getTimeStamp(timestr);
			if (Dao.checkurl(MainUtil.getMD5(contenturl)) == 1) {
				continue;
			}
			doc=http.get(contenturl);
			Elements body=doc.select(".TRS_Editor");
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
			article.setFrom_type(2);
			article.setIs_oversea(0);
			// 保存news
			Dao.insertNews(article,machineId);
		}
	
	}
	
}
