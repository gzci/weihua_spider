package com.spider.main;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.spider.bean.Seed;
import com.spider.util.ArticleGetNoss;
import com.spider.util.Dao;
import com.spider.util.HTTP;
import com.spider.util.MainUtil;
import com.spider.util.PtimePick;
import com.spider.util.WdUtils;
import com.spider.util.process;

public class TestCssclick {
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
					driver.get(ref_url);
					WdUtils.waitPageLoad(driver,1000);
					doc=Jsoup.parse(driver.getPageSource(),ref_url);
				String pagingcss=o.getPagingcss();
				String refcss=o.getRefcss();
				String reftimecss=o.getReftimecss();
				String reftitlecss=o.getReftitlecss();
				String urlcss = o.getUrlcss();
				String timecss = o.getTimecss();
				String titlecss = o.getTitlecss();
				String contcss = o.getContcss();
				String briefcss = o.getBriefcss();
				int out = 0;
				
				while (out != 1) {
					System.out.println(refcss);
					System.out.println("urlcss-->"+urlcss);
					System.out.println("请输入refcss表达式");
					refcss = new Scanner(System.in).nextLine();
					System.out.println(urlcss);
					System.out.println("请输入urlcss表达式");
					urlcss = new Scanner(System.in).nextLine();
					System.out.println(refcss+" "+urlcss);
					System.out.println(doc.selectFirst(refcss+" "+urlcss).attr("href"));
					System.out.println(reftitlecss);
					System.out.println("请输入reftitlecss表达式");
					
					reftitlecss = new Scanner(System.in).nextLine();
					if(!"".equals(reftitlecss)){
							System.out.println(doc.select(refcss+" "+reftitlecss).attr("title"));
							System.out.println(doc.select(refcss+" "+reftitlecss).attr("alt"));
							System.out.println(doc.selectFirst(refcss+" "+reftitlecss).text());
					}
					System.out.println(reftimecss);
					System.out.println("请输入reftimecss表达式");
					reftimecss = new Scanner(System.in).nextLine();
					if(!"".equals(reftimecss)){
						
					System.out.println(doc.selectFirst(refcss+" "+reftimecss).text());
					System.err.println(PtimePick.getTime(doc.selectFirst(refcss+" "+reftimecss).text()));
					}
					System.err.println("urlcss表达式是否正确:");
					out = new Scanner(System.in).nextInt();
				}
				out = 0;
				while (out != 1) {
					System.out.println(pagingcss);
					System.out.println("请输入pagingcss表达式");
					pagingcss = new Scanner(System.in).nextLine();
					if(!"".equals(pagingcss)){
						driver.findElementByCssSelector(pagingcss).click();
						WdUtils.waitPageLoad(driver,1000);
					}
					System.err.println("pagingcss表达式是否正确:");
					out = new Scanner(System.in).nextInt();
				}
				out = 0;
				driver.findElementByCssSelector(refcss+" "+urlcss).click();
				
				WdUtils.waitPageLoad(driver,1000);
				doc=Jsoup.parse(driver.getPageSource());
				if (doc == null) {
					System.out.println("URL访问失败:" + o.getId() + " -> " + o.getRef_url());
					continue;
				}
				String conurl=driver.getCurrentUrl();
				if("".equals(reftitlecss)){
					while (out != 1) {
						System.err.println(titlecss);
						System.err.println("请输入titlecss表达式:");
						titlecss = new Scanner(System.in).nextLine();
						System.out.println(doc.selectFirst(titlecss).text());
						System.err.println("titlecss表达式是否正确:");
						out = new Scanner(System.in).nextInt();
					}
				}
				
				out = 0;
				if("".equals(reftimecss)){
					while (out != 1) {
						System.err.println(timecss);
						System.err.println("请输入timecss表达式:");
						timecss = new Scanner(System.in).nextLine();
						String timecont=null;
						if(!"".equals(timecss)){
							timecont=doc.select(timecss).text();
							System.out.println(timecont);
							System.err.println(PtimePick.getTime(timecont));
						}
						
						System.err.println("timecss表达式是否正确:");
						out = new Scanner(System.in).nextInt();
					}
				}
				
				out = 0;

				while (out != 1) {
					System.out.println(contcss);
					System.err.println("请输入contcss表达式:");
					contcss = new Scanner(System.in).nextLine();
					writeMethod(ArticleGetNoss.getArticleContent(doc.select(contcss),conurl).html());
					
					driver.get("file:///E:/1.html");
					System.err.println("contcss表达式是否正确:");
					out = new Scanner(System.in).nextInt();
				}
				o.setPagingcss(pagingcss);
				o.setTitlecss(titlecss);
				o.setContcss(contcss);
				o.setUrlcss(urlcss);
				o.setTimecss(timecss);
				o.setBriefcss(briefcss);
				o.setRefcss(refcss);
				o.setReftimecss(reftimecss);
				o.setReftitlecss(reftitlecss);
				System.out.println("内容页表达式正确!!!");
				Dao.updateSeedCss(ref_url, o);
			}catch(Exception e){
				e.printStackTrace();
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
