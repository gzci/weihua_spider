package com.spider.post;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.jsoup.nodes.Document;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.spider.bean.Article;
import com.spider.util.Dao;
import com.spider.util.DateUtil2;
import com.spider.util.HTTP;
import com.spider.util.JsonPathSelector;
import com.spider.util.MainUtil;
import com.spider.util.RegexUtils;

public class readhub {
	private static HTTP http=HTTP.getInstance();
	private static Document doc=null;
	public static void run(int machineId) {
		doc=http.get("https://api.readhub.cn/topic?lastCursor=&pageSize=20");
		//System.out.println(doc.body().html());
		String titlejson="$..title";
		String timejson="$..updatedAt";
		String briefjson="$..summary";
		String idjson="$..id";
		List<String> listid=new ArrayList<String>();
		JSONObject o = (JSONObject) JSON.parse(doc.body().html());
		JSONArray arry =o.getJSONArray("data");
		if(arry==null){
			return;
		}
		for (int n = 0; n < arry.size(); n++) {
		JSONObject obj = (JSONObject) arry.get(n);
			String id=obj.getString("id");
			listid.add(id);
		}
		List<String> listtitle= new JsonPathSelector(titlejson).selectList(doc.body().html());
		
		List<String> listtime= new JsonPathSelector(timejson).selectList(doc.body().html());
		
		List<String> listbrief= new JsonPathSelector(briefjson).selectList(doc.body().html());
	
		for(int i=0 ;i<listid.size();i++){
		String id=listid.get(i);
		String url=null;
		url=String.format("https://api.readhub.cn/topic/instantview?topicId=%s", id);
		doc=http.get(url);
		url=String.format("https://readhub.cn/topic/%s", id);
		String title=null;
		String time=listtime.get(i);
		title=listtitle.get(i);
		String content=null;
		String sour_name="Readhub";
		String str=doc.body().html().toString()
				.replace("\\", "");
		if(str.length()>10){
			
			url=RegexUtils.regex("url:(.*?),", str.replace("\"", ""), 1);
		//	System.out.println(url);
			title=RegexUtils.regex("title:(.*?),", str.replace("\"", ""), 1);
		//	System.out.println(title);
			content=str.split("\",\"")[2].replace("content\":\"", "").replace("&quot;", "");
			//System.out.println(content);
			sour_name=RegexUtils.regex("siteName:(.*?),", str.replace("\"", ""), 1);
		//	System.out.println(sour_name);
		}
		
		String brief=listbrief.get(i);
			Timestamp publish_time=DateUtil2.getTimeStamp(time.split("T")[0]);
			Article article = new Article();
			article.setRef_url("https://readhub.me");
			article.setContent(content);
			article.setUrl(url);
			article.setSource_name(sour_name);
			article.setBrief(brief);
			article.setHtml_source(doc.html());
			article.setMd5_url(MainUtil.getMD5(String.format("https://readhub.cn/topic/%s", id)));
			article.setTitle(title);
			article.setPublish_time(publish_time);
			article.setCategory_id(0);
			
			article.setHit_tag("Readhub");
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
