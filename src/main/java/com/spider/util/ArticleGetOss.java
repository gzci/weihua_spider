package com.spider.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spider.bean.enclosure;

public class ArticleGetOss {
	private static HTTP http = HTTP.getInstance();
	private static Response res = null;
	private static Logger log = LoggerFactory.getLogger(ArticleGetOss.class);
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
			if(StringUtils.isBlank(enclosure_url)){
				enclosure_url = e.attr("abs:src");
			}
			if(enclosure_url.contains("http://www.okcis.cn/images2010")){
				e.remove();
				continue;
			}
			String oss_url=null;
			String erro_log=null;
			String anchor_text=e.text();
			String md5=null;
			String content_type=null;
			String postfix=null;
			String low=enclosure_url.toLowerCase();
			int download_code=0;
			for(String type:typelist){
				if(low.endsWith(type)){
					postfix=type;
					break;
				}
			}
			try{
				if(enclosure_url.equals("#")||enclosure_url.equals("javascript:void(0)")||"".equals(enclosure_url)){
					e.removeAttr("href");
					e.removeAttr("src");
					continue;
				}
				try{
					res = HTTP.getRe(enclosure_url);
				}catch(Exception re){
					erro_log=re.getMessage();
					download_code=3;
				}
				if(null==res){
					e.remove();
				}else{
					byte[] data = res.bodyAsBytes();
					md5=MainUtil.getMD5(enclosure_url);
					content_type=res.contentType();
					if(content_type.startsWith("text/html")||StringUtils.isBlank(content_type)){
						e.removeAttr("href");
						e.removeAttr("src");
						continue;
					}
					for (int i = 0; i < 3; i++) {
						// 最多尝试两次
						try {
							if(null!=postfix){
								oss_url = OssTools.uploadBytes((data),md5+"."+postfix,content_type);
							}else{
								oss_url = OssTools.uploadBytes((data),md5,content_type);
							}
							
							if (!StringUtils.isBlank(oss_url)) {
								break;
							}
						} catch (IOException e0) {
							log.error(e0.getMessage(),res.statusCode());
							erro_log=e0.getMessage()+res.statusCode();
							SleepUtils.sleep(5000);
						}
					}
				}
			}catch(Exception ew){
				erro_log=ew.getMessage()+res.statusCode();
				download_code=3;
			}
			//存储 
			enclosure o=new enclosure();
			o.setUrl(url);
			o.setEnclosure_url(enclosure_url);
			o.setOss_url(oss_url);
			o.setAnchor_text(anchor_text);
			o.setContent_type(content_type);
			o.setPostfix(postfix);
			o.setMd5(md5);
			if (StringUtils.isBlank(oss_url)) {
				oss_url=url;
				if(erro_log.contains("404")||erro_log.contains("50")||erro_log.contains("30")){
					download_code=3;
				}
				
			}
			o.setErro_log(erro_log);
			o.setDownload_code(download_code);
			e.attr("href",oss_url);
			e.attr("src",oss_url);
			Dao.insertEnclosure(o);
		}	
		return body;

	}

}
