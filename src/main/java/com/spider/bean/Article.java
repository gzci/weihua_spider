package com.spider.bean;

import java.sql.Timestamp;

public class Article {
	private Long id;//海量Id
	private String title;//标题
	private String anchor_text;//锚文本
	private String brief;//摘要
	private String content;//内容
	private String author;//作者【没有】
	private String source_name;//数据原始作者或网站
	private String origin_tag;//标签或者关键字，多个则使用"|"符号隔开【没有】
	private Timestamp publish_time;//发布时间
	
	private String url;//抓取URL
	private String md5_url;//抓取URL的md5值
	private String ref_url;//引用网址
	private Integer category_id;//分类ID
	private Integer from_type;//0-海量，1-神箭手，2-自爬数据
	private Integer origin_flag;//是否是原创，1表示原创，0表示非原创，-1表示未知
	private String hit_tag;//标签-某某频道下的某某栏目
	private Integer is_report;//是否是报告 0不是，1是
	private Integer is_pdf;//是否是pdf 0不是 1是
	private String pdf_url;//pdfurl
	private Integer is_fin_report;//是否是财报 0不是 1是
	private Integer is_oversea;//是否是海外数据，0-否，1-是
	private String html_source;
	private Integer url_type;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAnchor_text() {
		return anchor_text;
	}
	public void setAnchor_text(String anchor_text) {
		this.anchor_text = anchor_text;
	}
	public String getBrief() {
		return brief;
	}
	public void setBrief(String brief) {
		this.brief = brief;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}
	public String getOrigin_tag() {
		return origin_tag;
	}
	public void setOrigin_tag(String origin_tag) {
		this.origin_tag = origin_tag;
	}
	public Timestamp getPublish_time() {
		return publish_time;
	}
	public void setPublish_time(Timestamp publish_time) {
		this.publish_time = publish_time;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getMd5_url() {
		return md5_url;
	}
	public void setMd5_url(String md5_url) {
		this.md5_url = md5_url;
	}
	public String getRef_url() {
		return ref_url;
	}
	public void setRef_url(String ref_url) {
		this.ref_url = ref_url;
	}
	public Integer getCategory_id() {
		return category_id;
	}
	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}
	public Integer getFrom_type() {
		return from_type;
	}
	public void setFrom_type(Integer from_type) {
		this.from_type = from_type;
	}
	public Integer getOrigin_flag() {
		return origin_flag;
	}
	public void setOrigin_flag(Integer origin_flag) {
		this.origin_flag = origin_flag;
	}
	public String getHit_tag() {
		return hit_tag;
	}
	public void setHit_tag(String hit_tag) {
		this.hit_tag = hit_tag;
	}
	public Integer getIs_report() {
		return is_report;
	}
	public void setIs_report(Integer is_report) {
		this.is_report = is_report;
	}
	public Integer getIs_pdf() {
		return is_pdf;
	}
	public void setIs_pdf(Integer is_pdf) {
		this.is_pdf = is_pdf;
	}
	public String getPdf_url() {
		return pdf_url;
	}
	public void setPdf_url(String pdf_url) {
		this.pdf_url = pdf_url;
	}
	public Integer getIs_fin_report() {
		return is_fin_report;
	}
	public void setIs_fin_report(Integer is_fin_report) {
		this.is_fin_report = is_fin_report;
	}
	public Integer getIs_oversea() {
		return is_oversea;
	}
	public void setIs_oversea(Integer is_oversea) {
		this.is_oversea = is_oversea;
	}
	public String getHtml_source() {
		return html_source;
	}
	public void setHtml_source(String html_source) {
		this.html_source = html_source;
	}
	public Integer getUrl_type() {
		return url_type;
	}
	public void setUrl_type(Integer url_type) {
		this.url_type = url_type;
	}
	
	

}
