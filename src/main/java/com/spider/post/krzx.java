package com.spider.post;


import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.alibaba.fastjson.JSONObject;
import com.spider.bean.Article;
import com.spider.util.ArticleGetNoss;
import com.spider.util.Dao;
import com.spider.util.DateUtil2;
import com.spider.util.HTTP;
import com.spider.util.JsonPathSelector;
import com.spider.util.MainUtil;
import com.spider.util.ReplaceBlank;

public class krzx {
	private static HTTP http=HTTP.getInstance();
	private static Document doc=null;
	public static void run(int machineId) throws UnsupportedEncodingException {
		
		String ref_url0="http://36kr.com";
		String ref_url="https://36kr.com/api/search-column/mainsite?per_page=10&page=1";
		String source_name="36氪";
		
		try {
			parser(ref_url0,ref_url,machineId,source_name);
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static void parser(String ref_url0,String ref_url, int machineId,String Hit_tag) throws IllegalStateException, SQLException {
		
		int category_id = 0;
		doc=http.get(ref_url);
		String responsestr=doc.body().text();
		String start="(";
		String end=")";
		String urljson="$..id";
		String titlejson="$..title";
		String ptimejson="$..published_at";
		String briefjson="$..summary";
		
		List<String> listurl= new JsonPathSelector(urljson).selectList(responsestr);
		List<String> listtitle= new JsonPathSelector(titlejson).selectList(responsestr);
		List<String> listtime= new JsonPathSelector(ptimejson).selectList(responsestr);
		List<String> listbrief= new JsonPathSelector(briefjson).selectList(responsestr);
		for(int i=0 ;i<listurl.size();i++){
			try{
				String title=listtitle.get(i);
				String contenturl=String.format("https://36kr.com/api/post/%s/next", listurl.get(i));
				String timestr=listtime.get(i).split("\\+")[0].replace("T", " ");
				String brief=listbrief.get(i);
				Timestamp publish_time=DateUtil2.getTimeStamp(timestr);
				if (Dao.checkurl(MainUtil.getMD5(contenturl)) == 1) {
					continue;
				}
				doc=http.get(contenturl);
				if("".equals(doc)||null==doc){
					continue;
				}
				String cont=new JsonPathSelector("$..content").select(doc.text());
				Article article = new Article();
				article.setSource_name("36氪");
				article.setRef_url(ref_url0);
				article.setContent(cont);
				article.setUrl(String.format("https://36kr.com/p/%s.html", listurl.get(i)));
				article.setMd5_url(MainUtil.getMD5(String.format("https://36kr.com/p/%s.html", listurl.get(i))));
				article.setTitle(title);
				article.setPublish_time(publish_time);
				article.setCategory_id(category_id);
				
				article.setHit_tag(ReplaceBlank.remove(Hit_tag));
				article.setBrief(brief);
				article.setHtml_source(doc.html());
				// 自爬虫
				article.setFrom_type(2);
				article.setIs_oversea(0);
				// 保存news
				Dao.insertNews(article,machineId);
			}catch(Exception e){
				
			}
			
		}
	}
	
}
