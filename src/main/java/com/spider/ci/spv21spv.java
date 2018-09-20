package com.spider.ci;



import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.spider.bean.ci.pricenews;
import com.spider.util.ArticleGetOss;
import com.spider.util.Dao;
import com.spider.util.HTTP;
import com.spider.util.MainUtil;
import com.spider.util.PtimePick;

public class spv21spv {
	private static HTTP http = HTTP.getInstance();
	private static Document doc = null;

	public static void main() {
		String url = "http://www.21spv.com/quote/list.php?catid=324";
		doc = http.get(url);
		Elements es=doc.select(".catlist_li a");
		String source_name="阳光工匠光伏网";
		for(Element e:es){
			String contenturl=e.attr("abs:href");
			String time=PtimePick.getTime(e.text());
			String title=e.text();
			doc=http.get(contenturl);
			String processedContent=ArticleGetOss.getArticleContent(doc.select("#content img"), contenturl).outerHtml();
			String content=doc.html();
			pricenews o = new pricenews();
			o.setContent(content);
			o.setProcessedContent(processedContent);
			o.setUrl(contenturl);
			o.setPublishTime(time);
			o.setTitle(title);
			o.setSourceName(source_name);
			o.setMd5(MainUtil.getMD5(title));
			Dao.insertPriceNews(o);
		}

		
	}

}
