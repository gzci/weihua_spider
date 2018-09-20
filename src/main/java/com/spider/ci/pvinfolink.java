package com.spider.ci;


import org.jsoup.nodes.Document;
import com.spider.bean.ci.pricenews;
import com.spider.util.ArticleGetNoss;
import com.spider.util.Dao;
import com.spider.util.HTTP;
import com.spider.util.MainUtil;
import com.spider.util.PtimePick;

public class pvinfolink {
	private static HTTP http=HTTP.getInstance();
	private static Document doc=null;
	public static void main() {
		String url="https://www.pvinfolink.com/post.php?sn=2";
		doc=http.get(url);
		
		String time=PtimePick.getTime(doc.select("h6.last-update span").text());
		
		
		String title="pvinfolink-"+time;
		String source_name="pvinfolink";
		String content=doc.html();
		String processedContent=ArticleGetNoss.getArticleContent(doc.select(".news-price-section"),url).outerHtml().replace("研究方法及免责声明", "");
		pricenews o=new pricenews();
		
			o.setContent(content);
			o.setProcessedContent(processedContent);
			o.setUrl(url);
			o.setPublishTime(time);
			o.setTitle(title);
			o.setSourceName(source_name);
			o.setMd5(MainUtil.getMD5(title));
			Dao.insertPriceNews(o);
		
		
	}

}
