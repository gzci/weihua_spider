package com.spider.post;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.spider.bean.Article;
import com.spider.main.TestCssyuan;
import com.spider.util.ArticleGetNoss;
import com.spider.util.Dao;
import com.spider.util.DateUtil2;
import com.spider.util.HTTP;
import com.spider.util.JsonPathSelector;
import com.spider.util.MainUtil;
import com.spider.util.ReplaceBlank;


public class kr {
	private static HTTP http=HTTP.getInstance();
	private static Document doc=null;
	public static void run(int machineId) {
		doc=http.get("http://36kr.com/api/newsflash?b_id=&per_page=30");
		String urljson="$..news_url";
		String titlejson="$..title";
		String idjson="$..id";
		String timejson="$..published_at";
		String briefjson="$..description";
		List<String> listurl= new JsonPathSelector(urljson).selectList(doc.body().html());
		List<String> listtitle= new JsonPathSelector(titlejson).selectList(doc.body().html());
		List<String> listid= new JsonPathSelector(idjson).selectList(doc.body().html());
		List<String> listtime= new JsonPathSelector(timejson).selectList(doc.body().html());
		List<String> listbrief= new JsonPathSelector(briefjson).selectList(doc.body().html());
		for(int i=0 ;i<listurl.size();i++){
			String title=listtitle.get(i);
			String cont=null;
			String timestr=listtime.get(i);
			String brief=listbrief.get(i);
			String url=String.format("http://36kr.com/newsflashes/%s", listid.get(i));
			
			Timestamp publish_time=DateUtil2.getTimeStamp(timestr);
			Article article = new Article();
			String sour_name="36氪";
			article.setRef_url("http://36kr.com/newsflashes");
			article.setContent(cont);
			article.setUrl(url);
			article.setSource_name(sour_name);
			article.setBrief(brief);
			article.setMd5_url(MainUtil.getMD5(url));
			article.setTitle(title);
			article.setPublish_time(publish_time);
			article.setCategory_id(0);
		
			article.setHit_tag("36氪-7*24小时快讯");
			// 自爬虫
			article.setFrom_type(2);
			article.setIs_oversea(0);
			// 保存news
			try {
				Dao.insertNews(article,machineId);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
	}

}
