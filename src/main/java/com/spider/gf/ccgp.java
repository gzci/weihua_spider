package com.spider.gf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.spider.util.HTTP;
import com.spider.util.SleepUtils;


public class ccgp {
	private static HTTP http=HTTP.getInstance();
	private static Document doc=null;
	private static RemoteWebDriver driver = null;
	private static ChromeOptions options=null;
	static{
		driver = new ChromeDriver();
	}
	public static void main(String[] args) {
		String url=null;
		String start="";
		Map<String,String> map=new HashMap<String,String>();
		for(int year=2005;year<2013;year++){
		url=String.format("http://search.ccgp.gov.cn/oldsearch?searchtype=1&page_index=1&bidSort=0&buyerName=&projectId=&pinMu=0&bidType=0&dbselect=bidx&kw=&start_time=%s%3A01%3A01&end_time=%s%3A12%3A31&timeType=6&displayZone=&zoneId=&agentName=", year,year);	
		driver.get("http://search.ccgp.gov.cn/oldsearch?searchtype=1&page_index=1&dbselect=infox&kw=&buyerName=&projectId=&start_time=2012:12:01&end_time=2012:12:31&timeType=1&bidSort=0&pinMu=0&bidType=0&displayZone=&zoneId=&agentName=");
		String ss="";
		for(;;){
			if(ss.equals(driver.findElementByCssSelector(".vT-srch-result-list-bid li a").getText())){
				break;
			}
			ss=driver.findElementByCssSelector(".vT-srch-result-list-bid li a").getText();
			driver.findElementByCssSelector(".pager .next").click();
			SleepUtils.sleep(1000*10);
			System.out.println(driver.findElementByCssSelector(".vT-srch-result-list-bid li a").getText());
			map.put(driver.getCurrentUrl(), driver.getPageSource());
		}
		}
	}

}
