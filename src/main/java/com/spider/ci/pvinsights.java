package com.spider.ci;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.spider.bean.ci.pricenews;
import com.spider.util.ArticleGetNoss;
import com.spider.util.Dao;
import com.spider.util.DateUtil2;
import com.spider.util.HTTP;
import com.spider.util.MainUtil;
import com.spider.util.PtimePick;

public class pvinsights {
	private static HTTP http = HTTP.getInstance();
	private static Document doc = null;

	public static void main() {
		String url = "http://pvinsights.com/index.php";
		doc = http.get(url);

		String time = PtimePick
				.getTime(doc.select("table[style~=DXImageTransform]:eq(0) tr[bgcolor=#DDFFFF] td:eq(2)").text());

		String title = "pvinsights-" + time;
		String source_name = "pvinsights";
		String content = doc.html();

		for (Element e : doc.getElementsContainingOwnText("Visit here for more")) {

			e.parent().parent().parent().parent().remove();
		}

		String processedContent = ArticleGetNoss.getArticleContent(doc.select("table[style~=DXImageTransform]"),url)
				.outerHtml();
		pricenews o = new pricenews();

		o.setContent(content);
		o.setProcessedContent(processedContent);
		o.setUrl(url);
		o.setPublishTime(time);
		o.setTitle(title);
		o.setMd5(MainUtil.getMD5(title));
		o.setSourceName(source_name);
		Dao.insertPriceNews(o);

	}

}
