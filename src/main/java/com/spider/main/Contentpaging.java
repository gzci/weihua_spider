
package com.spider.main;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.spider.bean.Article;
import com.spider.bean.Fethcount;
import com.spider.bean.Seed;
import com.spider.util.ArticleGetNoss;
import com.spider.util.Daopaging;
import com.spider.util.DateUtil2;
import com.spider.util.HTTP;
import com.spider.util.MainUtil;
import com.spider.util.PtimePick;
import com.spider.util.ReplaceBlank;
import com.spider.util.WdUtils;
import com.spider.util.webdriverChrom;
import com.spider.util.webdriverPh;

import Main.AbstractFiles;
public class Contentpaging {
	private static final Logger LOG = LoggerFactory.getLogger(Contentpaging.class);
	protected Document doc;
	private static webdriverChrom web1=webdriverChrom.getInstance();
	private static RemoteWebDriver driverout=web1.get("https://www.baidu.com/");
	private static webdriverPh web2=webdriverPh.getInstance();
	private static RemoteWebDriver driverin=web2.get("https://www.baidu.com/");
	private static String urlKey = "";
	public static void main(String[] args) throws Exception {
	}
	public static void parser(Seed seed, int machineId) throws IllegalStateException, SQLException {
		
		String refcss=seed.getRefcss();
		String urlcss = seed.getUrlcss();
		String reftimecss=seed.getReftimecss();
		String reftitlecss=seed.getReftitlecss();
		String pagingcss=seed.getPagingcss();
		String ref_url = seed.getRef_url();
		String titlecss = seed.getTitlecss();
		String timecss = seed.getTimecss();
		String briefcss = seed.getBriefcss();
		String contcss = seed.getContcss();
		String source_name = seed.getSource_name();
		int category_id = seed.getCategory_id();
		int is_oversea = seed.getIs_oversea();
		String hit_tag = seed.getHit_tag();
		JSONObject data = new JSONObject();
		Timestamp get_time = seed.getGet_time();
		String ref_url0=ref_url;
		int num = 0;
		Document doc = null;
		try{
			driverout.get(ref_url);
			WdUtils.waitPageLoad(driverout,2000);
		}catch(Exception eo){
			throw new IllegalStateException("http -->timeout");
		}
		if(driverout.getPageSource().contains("无法访问此网站")){
			driverout.get(ref_url);
		}
	
		for(;;){
			try{
				ref_url=driverout.getCurrentUrl();
			}catch(Exception e1){
				return;
			}
			
			System.out.println(ref_url);
			doc=Jsoup.parse(driverout.getPageSource(),ref_url);
			if(null==doc){
				Daopaging.updateSeed(ref_url);
				Daopaging.seedLog(ref_url, null, "doc is null http..timeout");
				throw new IllegalStateException(ref_url + "-->doc is null http..timeout");
			}
			
			Elements es=doc.select(refcss);
			
			if(es.size()==0){
				Daopaging.seedLog(ref_url, null, "url css erro");
				throw new IllegalStateException(ref_url + "-->url css erro");
			}
			if(urlKey.equals(es.get(0).select(urlcss).attr("abs:href"))){
				return;
			}
			urlKey=es.get(0).select(urlcss).attr("abs:href");
			for (Element e : es) {
				
				data.clear();
				String title=null;
				if(null!=reftitlecss&&!"".equals(reftitlecss)){
					title=e.select(reftitlecss).attr("title");
					if(null==title||"".equals(title)){
						title=e.select(reftitlecss).attr("alt");
						if(null==title||"".equals(title)){
							title=e.select(reftitlecss).text();
						}
					}
				}
				String time=null; 
				if(null!=reftimecss&&!"".equals(reftimecss)){
					time=e.select(reftimecss).text();
				}
			
				String contenturl = e.select(urlcss).attr("abs:href");
				if(!contenturl.contains("http")){
					continue;
				}
				
				if (Daopaging.checkurl(MainUtil.getMD5(contenturl)) == 1) {
					continue;
				}
				num++;
				data.put("ref_url", ref_url);
				data.put("time", time);
				data.put("timecss", timecss);
				data.put("title", title);
				data.put("titlecss", titlecss);
				data.put("contcss", contcss);
				data.put("briefcss", briefcss);
				data.put("category_id", category_id);
				data.put("machineId", machineId);// 哪个机器
				data.put("source_name", source_name);
				data.put("is_oversea", is_oversea);
				data.put("hit_tag", hit_tag);
				// 内容下载
				try{
					fetcher(contenturl, data);
				}catch(IllegalStateException e2){
					if(e2.getMessage().endsWith("content erro")){
						break;
					}
					LOG.error(e2.getMessage());
				}
			}
				
			try{
				if(null==pagingcss||"".equals(pagingcss)){
					try{
						driverout.findElementByPartialLinkText("下一页").click();
					}catch(Exception e0){
						try{
							driverout.findElementByPartialLinkText("下页").click();
						}catch(Exception e1){
							try{
								driverout.findElementByCssSelector("a[title=下一页]").click();
							}catch(Exception e2){
								try{
									driverout.findElementByCssSelector("a[title=下页]").click();	
								}catch(Exception e3){
									try{
										driverout.findElementByPartialLinkText("Go").click();	
									}catch(Exception e4){
										try{
											driverout.findElementByPartialLinkText("后页").click();	
										}catch(Exception e5){
											break;
										}
									}
									
								}
								
							}
							
						}
						
					}
				}else if("0".equals(pagingcss)){
					break;
				}else{
					try{
						driverout.findElementByCssSelector(pagingcss).click();
					}catch(Exception e4){
						
						LOG.error("pagingcss is erro");
						break;
					}
				}
			}catch(Exception er2){
				
			}
			
			WdUtils.waitPageLoad(driverout,2000);
		}
		Timestamp end_time = Daopaging.updateSeed(ref_url0);
		Fethcount fethcount = new Fethcount();
		fethcount.setRef_url(ref_url);
		fethcount.setGet_time(get_time);
		fethcount.setEnd_time(end_time);
		fethcount.setNum(num);
		Daopaging.articleCount(fethcount);

	}

	// 内容下载
	public static void fetcher(String contenturl, JSONObject data) throws IllegalStateException, SQLException {
		Document doc = null;
		try{
			driverin.get(contenturl);
			WdUtils.waitPageLoad(driverin,2000);
		}catch(Exception eo){
			throw new IllegalStateException("http -->timeout");
		}
		doc=Jsoup.parse(driverin.getPageSource(),contenturl);
		if(null==doc){
			Daopaging.seedLog(data.getString("ref_url"), contenturl, "doc is null http..timeout");
			throw new IllegalStateException(contenturl + "-->doc is null http..timeout");
		}
		
		String title =data.getString("title");
		try{
			if(null==title || "".equals(title)){
				title = doc.selectFirst(data.getString("titlecss")).text();
			}
		}catch(Exception e){
			Daopaging.seedLog(data.getString("ref_url"), contenturl, "title erro");
			throw new IllegalStateException(contenturl + "-->title erro");
		}
		String ptime = null;
		Timestamp publish_time = null;
		try {
			String timecont=data.getString("time");
			try{
				if(null==timecont || "".equals(timecont)){
					timecont=doc.select(data.getString("timecss")).text();
				}
			}catch(Exception e){
				timecont=new Timestamp(System.currentTimeMillis()).toString().split(" ")[0];
			}
			if("".equals(timecont.trim())){
				timecont=new Timestamp(System.currentTimeMillis()).toString().split(" ")[0];
			}
			ptime = PtimePick.getTime(timecont);
			publish_time = DateUtil2.getTimeStamp(ptime);
			if(publish_time.compareTo(new Timestamp(System.currentTimeMillis()))>0){
				Daopaging.seedLog(data.getString("ref_url"), contenturl, "time>now");
				throw new IllegalStateException(contenturl + "-->time>now");
			}
		} catch (IllegalStateException e) {
			Daopaging.seedLog(data.getString("ref_url"), contenturl, "time erro-->"+e.getMessage());
			throw new IllegalStateException(contenturl + "-->time erro-->"+e.getMessage());
		}
		
		Elements body = null;
		String content=null;
		try{
			body = doc.select(data.getString("contcss"));
			body = ArticleGetNoss.getArticleContent(body,contenturl);
			content = body.outerHtml().replaceAll("(上|下)一篇(:|：)", "").replace("<p> &nbsp; </p>", "")
					.replace("<p>\\s+</p>", "").trim();
			if(content.trim().length()==0){
				throw new IllegalStateException(contenturl+"-->content erro or timeout");
			}
		}catch(Exception e){
			Daopaging.seedLog(data.getString("ref_url"), contenturl, "content erro-->"+e.getMessage());
		
			throw new IllegalStateException(contenturl+"-->content erro");
		}
		
		// 摘要处理
		String brief = null;
//		if (null != data.getString("briefcss") && !"".equals(data.getString("briefcss"))) {
//			try{
//			brief = doc.selectFirst(data.getString("briefcss")).text().replace("摘要:", "").replace("摘要：", "").replace("摘要", "");
//			
//			}catch(Exception e){
//				Daopaging.seedLog(data.getString("ref_url"), contenturl, "brief erro-->"+e.getMessage());
//				throw new IllegalStateException(contenturl+"-->brief erro");
//				
//			}
//		}
		
		 brief = AbstractFiles.getAbstractFromStr(body.text(), (float) 0.1);
		 if (brief.contains("本文不适合做摘要处理")) {
			brief="";
		 }
		brief = ReplaceBlank.remove(brief).replace(" ", "").replace(" ", "").trim();
		Article article = new Article();
		article.setSource_name(ReplaceBlank.remove(data.getString("source_name")));
		article.setRef_url(data.getString("ref_url"));
		article.setContent(content);
		article.setUrl(contenturl);
		article.setMd5_url(MainUtil.getMD5(contenturl));
		article.setTitle(title);
		article.setBrief(brief);
		article.setPublish_time(publish_time);
		article.setCategory_id(data.getInteger("category_id"));
		
		article.setHtml_source(doc.html());
		// 自爬虫
		article.setFrom_type(2);
		article.setHit_tag(data.getString("hit_tag"));
		article.setIs_oversea(data.getInteger("is_oversea"));
		// 保存news
		Daopaging.insertNews(article, data.getInteger("machineId"));

	}
}
