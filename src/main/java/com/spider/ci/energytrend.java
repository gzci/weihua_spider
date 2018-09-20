package com.spider.ci;

import org.jsoup.nodes.Document;

import com.spider.bean.ci.pricenews;
import com.spider.util.ArticleGetNoss;
import com.spider.util.Dao;
import com.spider.util.HTTP;
import com.spider.util.MainUtil;
import com.spider.util.PtimePick;

public class energytrend {
	private static HTTP http = HTTP.getInstance();
	private static Document doc = null;

	public static void main() {
		String url = "https://www.energytrend.cn/solar-price.html";
		doc = http.get(url);

		String time = PtimePick.getTime(doc.select("#tab-content .left_tab .update").text());

		String title = "集邦新能源-" + time;
		String source_name = "集邦新能源";
		String content = doc.html();
		doc.getElementsByAttributeValue("title", "成为付费会员即可获得完整报价").remove();
		String processedContent = ArticleGetNoss.getArticleContent(doc.select("#tab-content .left_tab"),url).outerHtml();
		pricenews o = new pricenews();

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
