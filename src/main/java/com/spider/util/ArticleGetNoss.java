package com.spider.util;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ArticleGetNoss {
	private static HTTP http = HTTP.getInstance();
	private static Response res = null;
	private static Logger log = LoggerFactory.getLogger(ArticleGetNoss.class);
	private static List<String> typelist = new ArrayList<String>();
	static{
		typelist.add("jpeg");
		typelist.add("jpg");
		typelist.add("gif");
		typelist.add("png");
		typelist.add("txt");
		typelist.add("pdf");
		typelist.add("doc");
		typelist.add("docx");
		typelist.add("ppt");
		typelist.add("xls");
		typelist.add("xlsx");
		typelist.add("zip");
		typelist.add("rar");
		typelist.add("txt");
	}
	public static void main(String[] args) {

		String url = "http://www.21spv.com/quote/show.php?itemid=1240";
		Document doc = http.get(url);
		Elements es = doc.select("img");

		//System.out.println(getArticleContent(es));
	}
	public static Elements getArticleContent(Elements body,String url) {
		
		body.select("script,noscript,style,iframe,button").remove();
		for (Element e : body.select("a[href],img[href],embed[href],a[src],img[src],embed[src]")) {
			String enclosure_url = e.attr("abs:href");
			String content_type=null;
			if(enclosure_url.equals("#")||enclosure_url.equals("javascript:void(0)")||"".equals(enclosure_url)){
				e.removeAttr("href");
				e.removeAttr("src");
				continue;
			}
			res = HTTP.getRe(enclosure_url);
			if(null==res){
				e.removeAttr("href");
				e.removeAttr("src");
				continue;
			}
			content_type=res.contentType();
			if(content_type.startsWith("text/html")||StringUtils.isBlank(content_type)){
				e.removeAttr("href");
				e.removeAttr("src");
				continue;
			}
			e.attr("href",enclosure_url);
			e.attr("src",enclosure_url);
		}	
		return body;

	}

}
