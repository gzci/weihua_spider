package com.spider.main;

import java.io.FileWriter;
import java.io.IOException;

public class checkAll {
//	private static final Logger log = Logger.getLogger(checkAll.class);
//	public static void main(String[] args) throws IOException {
//		String fileNamespider="E:/url.html";
//		FileWriter spider=new FileWriter(fileNamespider,true);
//		HTTP http = HTTP.getInstance();
//		Document doc=null;
//		List<String> urlall=new ArrayList<String>();
//		List<Seed> list=Dao.selectSeedlist();
//		for(Seed seed:list){
//			String ref_url=seed.getRef_url();
//			String urlcss=seed.getUrlcss();
//			
//			for (int i = 0; i < 3; i++) {
//				doc=http.get(ref_url);
//				if (null != doc) {
//					break;
//				}
//			}
//			List<String> urllist=Dao.getAllurl(ref_url);
//			
//			for (Element e : doc.select(urlcss)) {
//				String helperUrl=e.attr("abs:href");
//				System.out.println(helperUrl);
////				if(urllist.contains(helperUrl)){
////					log.info("已抓取");
//////				}else{
//////					writeMethod2(spider,ref_url+"	-->"+helperUrl);
//////				}
//					
//			}
//			
//		}
//
//	}
	 public static void writeMethod2( FileWriter writer,String s)
     {
           
             try
             {
                     writer.write("\n"+s);
                     writer.flush();
             } catch (IOException e)
             {
                     e.printStackTrace();
             }
     }

}
