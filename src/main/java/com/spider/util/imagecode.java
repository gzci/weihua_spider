package com.spider.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.poi.util.StringUtil;
import org.jsoup.Connection.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;


public class imagecode {
	private static final Logger log = LoggerFactory.getLogger(imagecode.class);
	private static HTTP http=HTTP.getInstance();
	public static void main(String[] args) throws Exception {
		Response res = null;
		for(;;){
			res=http.getRe("http://new.okcis.cn/php/checkUser/code.php");
			String img64 = java.util.Base64.getEncoder().encodeToString(res.bodyAsBytes());
			
			try {
				String imgcode = getCode(img64);
				System.out.println(imgcode);
				if(!StringUtils.isBlank(imgcode)){
					break;
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}
		
		
	}
	// 阿里云打码###############
		public static String getCode(String img64) throws Exception {
			 String host = "http://yzmplus.market.alicloudapi.com";
			    String path = "/fzyzm";
			    String method = "POST";
			    String appcode = "1ba0bab1011f477c98ab60a148d335ac";
			    Map<String, String> headers = new HashMap<String, String>();
			    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
			    headers.put("Authorization", "APPCODE " + appcode);
			    //根据API的要求，定义相对应的Content-Type
			    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			    Map<String, String> querys = new HashMap<String, String>();
			    Map<String, String> bodys = new HashMap<String, String>();
			    bodys.put("v_pic", img64);
			    bodys.put("v_type", "js");
			    String code=null;
			    try {
			    	HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
			    	
			    	JSONObject json = (JSONObject) JSON.parse(EntityUtils.toString(response.getEntity()));
				
					code = json.getString("v_code");
			    } catch (Exception e) {
			    	log.error(e.getMessage());
			    }
			return code;

		

		}
}
