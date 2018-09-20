package com.spider.util;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class MainUtil {

	public static void main(String[] args) {
		getMD5("333");

	}
	// 获取当前偏移ID, 格式为 YYYY_mm_dd0
		public static String getTodayOffsetId() {
			Date currentTime = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String today = formatter.format(currentTime);
			String todayOffsetId = today.replace("-", "_");
			todayOffsetId = todayOffsetId + "0";
			return todayOffsetId;
		}
	// MD5加密方法
	public static String getMD5(String str) {
		try {

			MessageDigest md = MessageDigest.getInstance("MD5");

			md.update(str.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}

			String sign = buf.toString();
			//System.out.println(sign);
			return sign;
		} catch (Exception e) {

			System.out.println("error");
			return "error";
		}
	}

	// 安全加密参数，获取sign秘钥
	public static String getSign(Map<String, String> params) {
		String sign = "";
		Map<String, String> treeMap = new java.util.TreeMap<String, String>();
		if (params != null) {
			if (params != null && params.size() > 0) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					treeMap.put(entry.getKey(), entry.getValue());
				}
			}
		}
		StringBuffer buffer = new StringBuffer();
		for (Map.Entry<String, String> entry : treeMap.entrySet()) {
			buffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}

		buffer.deleteCharAt(buffer.length() - 1);
		buffer.append("hylanda");
		sign = getMD5(buffer.toString());

		return sign;
	}

	// 拼接请求参数
	public static String array2string(Map<String, String> params) {
		String sign = "";
		Map<String, String> treeMap = new java.util.TreeMap<String, String>();
		if (params != null) {
			if (params != null && params.size() > 0) {
				for (Map.Entry<String, String> entry : params.entrySet()) {
					treeMap.put(entry.getKey(), entry.getValue());
				}
			}
		}

		StringBuffer buffer = new StringBuffer();
		for (Map.Entry<String, String> entry : treeMap.entrySet()) {
			buffer.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}

		buffer.deleteCharAt(buffer.length() - 1);
		return buffer.toString();
	}

}
