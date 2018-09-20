package com.spider.util;

import java.io.IOException;
import java.net.Authenticator;
import java.net.InetSocketAddress;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;

public class HTTP {
	// 代理隧道验证信息
	final static String ProxyUser = "H5HX69836P9R766D";
	final static String ProxyPass = "AB6B72148B0D650D";
	// 代理服务器
	final static String ProxyHost = "http-dyn.abuyun.com";
	final static Integer ProxyPort = 9020;
	private static HTTP instance = null;
	private static Map<String, String> headers = new HashMap<String, String>();
	@SuppressWarnings("unused")
	private static String cookie;

	
	public static void checkCookies(String cookie){
		headers.put("Cookie",cookie);
		
	}
	public static HTTP getInstance() {
		if (instance == null) {
			synchronized (HTTP.class) {
				if (instance == null) {
					instance = new HTTP();
				}
			}
		}
		return instance;
	}

	static {
		headers.put("Cache-Control", "no-cache");
		headers.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		headers.put("Accept-Encoding", "gzip, deflate, br");
		headers.put("Accept-Language", "zh-CN,zh;q=0.9");
		headers.put("Cache-Control", "max-age=0");
		headers.put("Connection", "Keep-Alive");
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:59.0) Gecko/20100101 Firefox/59.0");
	}
	public static Response getRePost(String url,Map<String,String> data) {
		Response res = null;
	
		try {
			for(int i=0;i<3;i++){
				res = Jsoup.connect(url).ignoreContentType(true).headers(headers).data(data).validateTLSCertificates(false)
						.method(Method.POST).execute();
			if(null!=res){
				break;
			}
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return res;
	}
	public static Response getRe(String url) {
		Response res = null;
	
		try {
			for(int i=0;i<3;i++){
				res = Jsoup.connect(url).ignoreContentType(true).headers(headers).validateTLSCertificates(false)
						.method(Method.GET).execute();
			if(null!=res){
				break;
			}
			}
			
		} catch (Exception e) {
			throw new IllegalStateException(url + e.getMessage()+"-->"+res.statusCode());
		}
		return res;
	}
	public static Document getProxy(String url, int timeout) {
		Authenticator.setDefault(new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(ProxyUser, ProxyPass.toCharArray());
			}
		});

		Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ProxyHost, ProxyPort));
		Document doc = null;
		try {	
			doc = Jsoup.connect(url).ignoreContentType(true).validateTLSCertificates(false).timeout(timeout)
					.proxy(proxy).get();
			if (doc != null) {
				return doc;
			}

		} catch (IOException e) {
			//e.printStackTrace();
		}

		return null;
	}

	public Document get(String url) {
		Document doc = null;
		try {
			for(int i=0;i<3;i++){
				doc = Jsoup.connect(url).ignoreContentType(true).headers(headers).validateTLSCertificates(false).timeout(400000).followRedirects(true).get();
				if(null!=doc){
					break;
				}
			}
			
		//	System.out.println(url);
		} catch (Exception e) {
			
			//System.err.println(url);
			
		}
		return doc;
	}
	public Document getip(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).ignoreContentType(true).headers(headers).proxy("192.168.5.96", 80).validateTLSCertificates(false).timeout(400000).followRedirects(true).get();
		//	System.out.println(url);
		} catch (Exception e) {
			
			//System.err.println(url);
			
		}
		return doc;
	}
	public Document post(String url,Map<String,String>data) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).ignoreContentType(true).headers(headers).data(data).validateTLSCertificates(false).timeout(400000).followRedirects(true).post();
		//	System.out.println(url);
		} catch (Exception e) {
			
			//System.err.println(url);
			
		}
		return doc;
	}
	
	public Document post(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).ignoreContentType(true).headers(headers).validateTLSCertificates(false).timeout(400000).followRedirects(false).post();
		//	System.out.println(url);
		} catch (Exception e) {
			
			//System.err.println(url);
			
		}
		return doc;
	}

	public static void main(String[] args) {

//		long timesmap = System.currentTimeMillis();
//		String androidurl = "http://android.kuchuan.com/searchapp?kw=%s&page=1&count=20&date=%s";
//		androidurl = String.format(androidurl, "淘宝", timesmap);
//
//		System.out.println(getProxy(androidurl, 3000).body());
//		
		HTTP http=HTTP.getInstance();
		http.checkCookies("etag: \"d2b286b3dc1ce1:0\"");
		http.get("https://www.cnblogs.com/skins/nature/images/banner.gif");
		System.out.println(http);
		
	}

}
