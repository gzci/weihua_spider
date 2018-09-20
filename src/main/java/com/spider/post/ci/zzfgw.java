package com.spider.post.ci;


import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

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

public class zzfgw {
	private static HTTP http=HTTP.getInstance();
	private static Document doc=null;
	private static final Logger log = LoggerFactory.getLogger(zzfgw.class);
	public static void main(String[] args) throws Exception{
		run(11);
	}	
	public static void run(int machineId) throws Exception {
		String ref_url="http://www.zzfgw.gov.cn/xxgk_zcfg_list.asp?lmid=107106100";//20
		String source_name="湖南省株洲市发改委";
		String hit_tag="湖南省株洲市发改委-信息公开 -审批";
		String urlcss=".td_content .newstitle";
		try {
			for(int i=2;i<=20;i++){
				ref_url=ref_url+"&Page="+i;
				parser(ref_url,machineId,source_name,urlcss,hit_tag);
			}
		
//			ref_url="http://www.zzfgw.gov.cn/xxgk_zcfg_list.asp?lmid=107106101";//3
//			hit_tag="湖南省株洲市发改委-信息公开 -核准";
//			parser(ref_url,machineId,source_name,urlcss,hit_tag);
//			ref_url="http://www.zzfgw.gov.cn/xxgk_zcfg_list.asp?lmid=107106102";//3
//			hit_tag="湖南省株洲市发改委-信息公开 -备案";
//			parser(ref_url,machineId,source_name,urlcss,hit_tag);
//			ref_url="http://www.zzfgw.gov.cn/xxgk_zcfg_list.asp?lmid=107106103";//10
//			hit_tag="辽宁省鞍山市发改委-项目．投融资．招投标";
//			parser(ref_url,machineId,source_name,urlcss,hit_tag);
		} catch (IllegalStateException e) {
			throw new IllegalStateException( zzfgw.class+ "-->"+e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
	public static void parser(String ref_url,int machineId,String source_name,String urlcss,String hit_tag) throws IllegalStateException, SQLException {
	
		int category_id = 0;
		doc=http.get(ref_url);
		Elements es=doc.select(urlcss);
		for(int i=0 ;i<es.size();i++){
			try{
				String title=es.get(i).select("a").text();
				String contenturl=ref_url.replace("list", "content")+"&"+es.get(i).select("a").attr("abs:href").replace("http://www.zzfgw.gov.cn/xxgk_zcfg_view.asp?", "");
				String time=es.get(i).select("font").text();
				Timestamp publish_time=DateUtil2.getTimeStamp(PtimePick.getTime(time));
				if (Dao.checkurl(MainUtil.getMD5(contenturl)) == 1) {
					continue;
				}
				doc=http.get(contenturl);
				Elements body=doc.select(".udd25");
				body=ArticleGetNoss.getArticleContent(body,contenturl);
				String brief=null;
				 brief = AbstractFiles.getAbstractFromStr(body.text(), (float) 0.1);
				 if (brief.contains("本文不适合做摘要处理")) {
					brief="";
				 }
				String newJson = StringEscapeUtils.unescapeHtml4(body.outerHtml());
				
				Article article = new Article();
				article.setSource_name(ReplaceBlank.remove(source_name));
				article.setRef_url(ref_url);
				article.setContent(newJson);
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
				article.setIs_oversea(1);
				// 保存news
				Dao.insertNews(article,machineId);
			}catch(Exception e){
				
			}
			
		}
	}
	
}
