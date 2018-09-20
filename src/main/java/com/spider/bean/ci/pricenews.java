package com.spider.bean.ci;

import java.util.Date;

public class pricenews {
	private String title;//标题（网站+日期、微信公众号的直接用原有标题）
	private String publishTime;//发布时间
	private String url;//抓取url（网站就是网站、微信公众号的空着）
	private String sourceName;//数据来源
	private String content;//html页面
	private String processedContent;//抽取的正文
	private Date insertTime;//插入时间
	private Date updateTime;//更新时间
	private String md5;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSourceName() {
		return sourceName;
	}
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getProcessedContent() {
		return processedContent;
	}
	public void setProcessedContent(String processedContent) {
		this.processedContent = processedContent;
	}
	public Date getInsertTime() {
		return insertTime;
	}
	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getMd5() {
		return md5;
	}
	public void setMd5(String md5) {
		this.md5 = md5;
	}
	
	
	
}
