package com.spider.ci;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.spider.bean.ci.GFmodel;
import com.spider.util.ArticleGetOss;
import com.spider.util.CookieTool;
import com.spider.util.Dao;
import com.spider.util.DateUtil2;
import com.spider.util.HTTP;
import com.spider.util.IdGenerator;
import com.spider.util.MainUtil;
import com.spider.util.PtimePick;
import com.spider.util.RegexUtils;
import com.spider.util.SleepUtils;
import com.spider.util.WdUtils;
import com.spider.util.imagecode;
import com.spider.util.webdriverChrom;

import afu.org.checkerframework.checker.regex.RegexUtil;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
 
import javax.imageio.ImageIO;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.internal.WrapsDriver;
@Component
public class okcisold implements Runnable{
	private static HTTP http=new HTTP();
	private static Response res=null;
	private static Document doc=null;
	private static final Logger log = LoggerFactory.getLogger(okcisold.class);
	private static webdriverChrom web=null;
	private static RemoteWebDriver driver=null;
	private static String cookies;
	private static int n=1;
	@Override
	public void run() {
		try{
			web=webdriverChrom.getInstance();
			driver=web.get("https://www.baidu.com/");
			WdUtils.waitPageLoad(driver, 200);
			String name_password=Dao.getNP();
			CookieTool.loadThisHostCookie(driver);
			driver=web.get("http://www.okcis.cn/login/login");
			driver.get(driver.getCurrentUrl());
			System.out.println("load cookies.");
			WdUtils.waitPageLoad(driver, 200);
			System.out.println("等待登录...");
			for (;;) {
				driver.get("http://www.okcis.cn/php/myoffice/home.php");
				WdUtils.waitPageLoad(driver, 3000);
				if(driver.getPageSource().contains("崔志阳")){
					System.out.println("已经登录");
					break;
				}else{
					driver.findElementByCssSelector("#infoname1").clear();
					driver.findElementByCssSelector("#infopass1").clear();
					driver.findElementByCssSelector("#infoname1").sendKeys(name_password.split("#&#")[0]);
					driver.findElementByCssSelector("#infopass1").sendKeys(name_password.split("#&#")[1]);
						
					WebElement	element=driver.findElementByCssSelector("img[id=setcode]");
				
					File file=elementSnapshot(element);
					FileInputStream fin = new FileInputStream(file);
			        byte[] bytes  = new byte[fin.available()];
//			        File imageFile = new File("E://BeautyGirl.jpg");
//					// 创建输出流
//					FileOutputStream outStream = new FileOutputStream(imageFile);
//					// 写入数据
//					outStream.write(bytes);
//					// 关闭输出流
//					outStream.close();
			        fin.read(bytes);
			        fin.close();
					String image=java.util.Base64.getEncoder().encodeToString(bytes);
					String imgcode=  imagecode.getCode(image);
					System.out.println(imgcode);
					if(null==imgcode){
						continue;
					}
					driver.findElementByCssSelector("#codecenter").clear();
					driver.findElementByCssSelector("#codecenter").sendKeys(imgcode);
					SleepUtils.sleep(1000*5);
					driver.findElementByCssSelector("#loginCheck1").click();
					WdUtils.waitPageLoad(driver, 5000);
				}
			}
			cookies=CookieTool.storeCookie(driver);
			int max=100;
			String ref_url="";
			for(int i=1;i<=max;i++){
			ref_url="http://www.okcis.cn/as/keystr-%E5%85%89%E4%BC%8F/citystr-/timezb-10/search_column-un/searchTrue-realy/appear-content/page-"+i;
				//ref_url="http://www.okcis.cn/as/keystr-%E5%85%89%E4%BC%8F/citystr-/timezb-10/search_column-pj_hs/searchTrue-realy/appear-content/page-"+i;
				fetcher( ref_url);
			}
		
			
		}catch(Exception e){
			e.printStackTrace();
			log.error("driver http erro");
		}
		

	}
	public static void fetcher(String ref_url){

		driver.get(ref_url);
		WdUtils.waitPageLoad(driver, 300);
		doc=Jsoup.parse(driver.getPageSource(),driver.getCurrentUrl());
		for(int i=0;i<3;i++){
			if(null!=doc){
				break;
			}
		}
		String refcss=".xiangmu_ta_20140617 tr:gt(0)";
		String urlcss="td:eq(3) a";
		String project_typecss="td:eq(2)";
		String project_namecss="td:eq(3) a";
		String pub_timecss="td:eq(5)";
		String areasscss="td:eq(4)";
		Map<String,GFmodel> data=new HashMap<String,GFmodel>();
		for(Element es:doc.select(refcss)){
			String contenturl=es.select(urlcss).attr("abs:href");
			int result =0;
			result = Dao.checkurl(contenturl,"tab_put_on_record");
			if (result == 0) {
				result = Dao.checkurl(contenturl,"tab_raw_bidd");
				if(result == 0){
					result = Dao.checkurl(contenturl,"tab_rcc_project");
					
					if(result == 1){
						System.out.println("没有更新的信息");
						continue;
					}
					
				}else{
					continue;
				}
			}else {
				continue;
			}
			String project_name=es.select(project_namecss).attr("title").replace("-招标采购导航网", "");
			String area=es.select(areasscss).text();
			if(area.contains("-")){
				if(null!=area&&!"".equals(area)&&area.split("-")[0].equals(area.split("-")[1])){
					area=area.split("-")[0];
				}	
			}
			String city=null;
			if(area.contains("-")){
				String[]str =area.split("-");
				area=str[0];
				city=str[1];
			}
			String project_type=es.select(project_typecss).text().replace("[", "").replace("]", "");
		
		
		GFmodel gf = new GFmodel();
		gf.setCity(city);
		gf.setUrl(contenturl);
		gf.setArea(area);
		gf.setProject_name(project_name);
		gf.setPub_time(PtimePick.getTime(es.select(pub_timecss).text()));
		gf.setPub_timed(DateUtil2.getTimeStamp(PtimePick.getTime(es.select(pub_timecss).text())));
		gf.setProject_type(project_type);
		gf.setRowkey(MainUtil.getMD5(IdGenerator.getId(0, 8)+project_name));
		data.put(contenturl, gf);
		}
		fetchern(data);
	}
	public static void fetchern(Map<String,GFmodel> data){
		
		for (Map.Entry<String, GFmodel> entry : data.entrySet()) { 
			 String contenturl=entry.getKey();
			 int num=Dao.numTab();
			 log.info("今日已经抓取数据..."+num);
			 if(Dao.numTab()>=280){
				 log.info("请求数量已经达到上限今天停止抓取..."+num);
				 System.exit(0);
			 }
			 if(n%100==0){
				 log.info("休息一下..."+Dao.numTab());
				 System.exit(0);
			 }
			 driver.get(contenturl);
				WdUtils.waitPageLoad(driver, 300);
				if(driver.getPageSource().contains("common_error404.png")){
					continue;
				}
				doc=Jsoup.parse(driver.getPageSource(),contenturl);
				String html=doc.html();
			 GFmodel gf=entry.getValue();
			 String project_type=gf.getProject_type();
			 String processed_content=null;
			 gf.setHtml(html);
			 if(project_type.contains("核实项目")){
					processed_content=ArticleGetOss.getArticleContent(doc.select(".biaoti_h4_20140616+div.main_mz_20140615"),contenturl).html()+doc.select(".xmjs_p_20140616");
					
					String project_stage=null;
					project_stage=doc.select(".jbxx_ta_20140616 tr:eq(2) td div").text();
					long investment_amounts=0;
					String str=RegexUtils.regex("([0-9]+)", doc.select(".jbxx_ta_20140616 tr:eq(3) div").text(), 1)+"0000";
					
					investment_amounts=Long.valueOf(str);
					String project_remark=null;
					project_remark="计划竣工时间"+doc.select(".jbxx_ta_20140616 tr:eq(4) div").text().split("计划竣工时间")[1];
					if(project_remark.contains("200-")){
						project_remark=null;
					}
					String str2=doc.select(".xmjs_p_20140616").html();
					String project_address=null;
					project_address=RegexUtils.regex("项目地址：(.*?)<br>", str2, 1);
					String project_introduce=null;
					project_introduce=str2.split("<br>")[2];
					String proprietor=null;
					JSONObject obj = new JSONObject();
					String company=null;
					company=RegexUtils.regex("建设单位：(.*?)<br>", str2, 1);
					String contractor=null;
					contractor=RegexUtils.regex("联系人：(.*?)<br>", str2, 1);
					String phone=null;
					phone=RegexUtils.regex("<br> 电话：(.*?)<br>", str2, 1);
					String address=null;
					address=RegexUtils.regex("<br> 地址：(.*?)<br>", str2, 1);
					 obj.put("company", company);
		             obj.put("contractor", contractor);
		             obj.put("phone", phone);
		             obj.put("address", address);
		             proprietor=obj.toJSONString();
		             gf.setInvestment_amounts(investment_amounts);
		             gf.setProcessed_content(processed_content);
		             gf.setProject_address(project_address);
		             gf.setProject_introduce(project_introduce);
		             gf.setProject_remark(project_remark);
		             gf.setProprietor(proprietor);
		             gf.setProject_stage(project_stage);
		             if(StringUtils.isBlank(processed_content)){
		            	 continue;
		             }
		             Dao.insertNZJ(gf);
					
				}else if(project_type.contains("全程跟踪")){
					continue;
				}else if(project_type.contains("VIP")){
					continue;
				}else if(project_type.contains("独家")){
					continue;
				}else if(project_type.contains("拟建项目")){
					processed_content=ArticleGetOss.getArticleContent((doc.select(".main_mc_20140615:lt(2),.xxbt3,.xx")),contenturl).html();
					gf.setProcessed_content(processed_content);
					 if(StringUtils.isBlank(processed_content)){
		            	 continue;
		             }
					Dao.insertBA(gf);
				}else{
					processed_content=ArticleGetOss.getArticleContent((doc.select(".main_mc_20140615:lt(2),.xxbt3,.xx")),contenturl).html();
					gf.setProcessed_content(processed_content);
					 if(StringUtils.isBlank(processed_content)){
		            	 continue;
		             }
					Dao.insertZZ(gf);
				}
				int sleep=RandomUtils.nextInt(1000*60,1000*80);
				SleepUtils.sleep(sleep);
				n++;
			}
		
		
		
	}
	public static File elementSnapshot(WebElement element) throws Exception {
		//创建全屏截图
		WrapsDriver wrapsDriver = (WrapsDriver)element;
		File screen = ((TakesScreenshot)wrapsDriver.getWrappedDriver()).getScreenshotAs(OutputType.FILE);
		BufferedImage image = ImageIO.read(screen);
		//获取元素的高度、宽度
		int width = element.getSize().getWidth();
		int height = element.getSize().getHeight();
		
		//创建一个矩形使用上面的高度，和宽度
		Rectangle rect = new Rectangle(width, height);
		//元素坐标
		Point p = element.getLocation();
		BufferedImage img = image.getSubimage(p.getX(), p.getY(), rect.width, rect.height);
		ImageIO.write(img, "jpg", screen);
		return screen;
	}

}
