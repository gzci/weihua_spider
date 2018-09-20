package com.spider.bean;

import java.sql.Timestamp;

public class enclosure {
	private int id;//数据id
	private String url;//新闻的url
	private String anchor_text;//锚文本
	private String enclosure_url;//附件的url
	private String oss_url;//阿里云的url
	private String md5;//传给阿里的附件字节的MD5
	private int download_code;//下载状态码是否成功0成功,2不成功,可以重试下载,3不成功且不能下载:默认是0
	private String erro_log;//下载失败的日志信息
	private int retry_num;//重试次数默认是0
	private String content_type;//附件类型
	private String postfix;//附件后缀名
	private Timestamp insert_time;
	private Timestamp update_time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getAnchor_text() {
		return anchor_text;
	}
	public void setAnchor_text(String anchor_text) {
		this.anchor_text = anchor_text;
	}
	public String getEnclosure_url() {
		return enclosure_url;
	}
	public void setEnclosure_url(String enclosure_url) {
		this.enclosure_url = enclosure_url;
	}
	public String getOss_url() {
		return oss_url;
	}
	public void setOss_url(String oss_url) {
		this.oss_url = oss_url;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	public int getDownload_code() {
		return download_code;
	}
	public void setDownload_code(int download_code) {
		this.download_code = download_code;
	}
	public String getErro_log() {
		return erro_log;
	}
	public void setErro_log(String erro_log) {
		this.erro_log = erro_log;
	}
	public int getRetry_num() {
		return retry_num;
	}
	public void setRetry_num(int retry_num) {
		this.retry_num = retry_num;
	}
	public String getContent_type() {
		return content_type;
	}
	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}
	public String getPostfix() {
		return postfix;
	}
	public void setPostfix(String postfix) {
		this.postfix = postfix;
	}
	public Timestamp getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(Timestamp insert_time) {
		this.insert_time = insert_time;
	}
	public Timestamp getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Timestamp update_time) {
		this.update_time = update_time;
	}
	
	
}
