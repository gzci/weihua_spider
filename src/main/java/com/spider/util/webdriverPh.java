package com.spider.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class webdriverPh {
	private static webdriverPh instance = null;
	private static RemoteWebDriver driver=null;
	static{
		
		if (SystemUtils.IS_OS_LINUX) {
			 DesiredCapabilities dcaps = new DesiredCapabilities();
		      //ssl证书支持
		      dcaps.setCapability("acceptSslCerts", true);
		      //截屏支持
		      dcaps.setCapability("takesScreenshot", true);
		      //css搜索支持
		      dcaps.setCapability("cssSelectorsEnabled", true);
		      //js支持
		      dcaps.setJavascriptEnabled(true);
		      //驱动支持（第二参数表明的是你的phantomjs引擎所在的路径）
		      dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
		              "/usr/bin/phantomjs");
		      //创建无界面浏览器对象
		      driver = new PhantomJSDriver(dcaps);
		}else{

			driver = new ChromeDriver();
		}
		
     
	}
	public static void main(String[] args) {
		webdriverChrom web=webdriverChrom.getInstance();
		RemoteWebDriver driver=web.get("https://www.baidu.com/");
		System.out.println(driver.getPageSource());
		//driver.close();

	}
	public static webdriverPh getInstance() {
		if (instance == null) {
			synchronized (HTTP.class) {
				if (instance == null) {
					instance = new webdriverPh();
				}
			}
		}
		return instance;
	}
	public RemoteWebDriver get(String url) {
		try {
			driver.get(url);
//			driver.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS);
//			driver.manage().timeouts().setScriptTimeout(20,TimeUnit.SECONDS);
		} catch (Exception e) {
		}
		return driver;
	}
	
}
