package com.spider.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.spider.bean.Seed;
import com.spider.util.Dao;
import com.spider.util.HTTP;
import com.spider.util.MainUtil;
import com.spider.util.PtimePick;

public class TestCssyuan {
	private static HTTP http = HTTP.getInstance();
	private static Document doc;
	private static RemoteWebDriver driver = null;

	static {
	 driver = new ChromeDriver();
	// //driver.manage().window().maximize();
	// //driver.executeScript("window.scrollTo(0,500)");
	}

	public static void main(String[] args) {

		for (;;) {
			try{
				Seed o = Dao.selectSeedlist();
				System.out.println("检查:" + o.getId() + " -> " + o.getRef_url() + "\n\r");
				String ref_url = o.getRef_url();
				for (int i = 0; i < 3; i++) {
					doc = http.get(ref_url);
					doc=Jsoup.parse(doc.html(),ref_url);
					if (null != doc) {
						break;
					}
				}
				if (doc == null) {
					System.out.println("URL访问失败:" + o.getId() + " -> " + o.getRef_url());
					continue;
				}
				String urlcss = o.getUrlcss();
				String timecss = o.getTimecss();
				String titlecss = o.getTitlecss();
				String contcss = o.getContcss();
				String briefcss = o.getBriefcss();
				int out = 0;
				//System.out.println(doc);
				while (out != 1) {
					System.out.println(urlcss);
					System.out.println("请输入urlcss表达式");
					urlcss = new Scanner(System.in).nextLine();
					System.out.println(doc.select(urlcss).stream().map(e -> e.attr("abs:href")+"\n").collect(Collectors.toList()));
					System.err.println("urlcss表达式是否正确:");
					out = new Scanner(System.in).nextInt();
				}
				out = 0;
				String conurl = doc.selectFirst(urlcss).attr("abs:href");
				
				for (int i = 0; i < 3; i++) {
					doc = http.get(conurl);
					doc=Jsoup.parse(doc.html(),ref_url);
					if (null != doc) {
						break;
					}
				}
				if (doc == null) {
					System.out.println("URL访问失败:" + o.getId() + " -> " + o.getRef_url());
					continue;
				}
				
				System.out.println(conurl);
				while (out != 1) {
					System.err.println(titlecss);
					System.err.println("请输入titlecss表达式:");
					titlecss = new Scanner(System.in).nextLine();
					System.out.println(doc.selectFirst(titlecss).text());
					System.err.println("titlecss表达式是否正确:");
					out = new Scanner(System.in).nextInt();
				}
				out = 0;
				while (out != 1) {
					System.err.println(timecss);
					System.err.println("请输入timecss表达式:");
					timecss = new Scanner(System.in).nextLine();
					String timecont=doc.select(timecss).text();
					System.out.println(timecont);
					System.err.println(PtimePick.getTime(timecont));
					System.err.println("timecss表达式是否正确:");
					out = new Scanner(System.in).nextInt();
				}
				out = 0;

//				while (out != 1) {
//					System.out.println(briefcss);
//					System.out.println("文章是否有摘要？");
//					out = new Scanner(System.in).nextInt();
//					if(out==4){
//						break;
//					}
//					System.err.println("请输入briefcss表达式:");
//					briefcss = new Scanner(System.in).nextLine();
//					System.out.println(doc.select(briefcss).text());
//					System.err.println("briefcss表达式是否正确:");
//					out = new Scanner(System.in).nextInt();
//				}
//				out = 0;
				//driver =new ChromeDriver();
				while (out != 1) {
					System.out.println(contcss);
					System.err.println("请输入contcss表达式:");
					contcss = new Scanner(System.in).nextLine();
					writeMethod(doc.select(contcss).toString());
					
					driver.get("file:///E:/1.html");
					System.err.println("contcss表达式是否正确:");
					out = new Scanner(System.in).nextInt();
				}
			//	driver.quit();
				
				o.setPagingcss(null);
				o.setTitlecss(titlecss);
				o.setContcss(contcss);
				o.setUrlcss(urlcss);
				o.setTimecss(timecss);
				o.setBriefcss(briefcss);
				o.setRefcss(null);
				o.setReftimecss(null);
				o.setReftitlecss(null);
				System.out.println("内容页表达式正确!!!");
				Dao.updateSeedCss(ref_url, o);
			}catch(Exception e){
				e.printStackTrace();
//				driver.quit();
//				driver = new ChromeDriver();
			}
			

		}
	}
	 public static void writeMethod(String con)
     {
		 String fileNamespider="E:/1.html";
             try
             {
            	 FileWriter writer=new FileWriter(fileNamespider,false);
            	
                     writer.write("\n"+con);
                     writer.flush();
             } catch (IOException e)
             {
                     e.printStackTrace();
             }
     }
}
