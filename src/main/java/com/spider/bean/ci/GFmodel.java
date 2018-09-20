package com.spider.bean.ci;

import java.sql.Timestamp;

public class GFmodel {
	private String title;
	private String context;
	private String url;
	private String pub_time;
	private Timestamp  pub_timed;
	private String project_number;
	private String project_name;
	private String project_type;
	private String project_stage;
	private String rowkey;
	private String proprietor;
	private long investment_amounts;
	private String html;
	private String area;
	private String city;
	private String project_remark;
	private String project_address;
	private String processed_content;
	private String project_introduce;
	public String getProject_address() {
		return project_address;
	}
	public void setProject_address(String project_address) {
		this.project_address = project_address;
	}
	public String getHtml() {
		return html;
	}
	public void setHtml(String html) {
		this.html = html;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public long getInvestment_amounts() {
		return investment_amounts;
	}
	public void setInvestment_amounts(long investment_amounts) {
		this.investment_amounts = investment_amounts;
	}
	
	public Timestamp getPub_timed() {
		return pub_timed;
	}
	public void setPub_timed(Timestamp pub_timed) {
		this.pub_timed = pub_timed;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContext() {
		return context;
	}
	public void setContext(String context) {
		this.context = context;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getPub_time() {
		return pub_time;
	}
	public void setPub_time(String pub_time) {
		this.pub_time = pub_time;
	}
	public String getProject_number() {
		return project_number;
	}
	public void setProject_number(String project_number) {
		this.project_number = project_number;
	}
	public String getProject_name() {
		return project_name;
	}
	public void setProject_name(String project_name) {
		this.project_name = project_name;
	}
	public String getProject_type() {
		return project_type;
	}
	public void setProject_type(String project_type) {
		this.project_type = project_type;
	}
	public String getProject_stage() {
		return project_stage;
	}
	public void setProject_stage(String project_stage) {
		this.project_stage = project_stage;
	}
	public String getRowkey() {
		return rowkey;
	}
	public void setRowkey(String rowkey) {
		this.rowkey = rowkey;
	}
	
	public String getProprietor() {
		return proprietor;
	}
	public void setProprietor(String proprietor) {
		this.proprietor = proprietor;
	}
	public String getProject_remark() {
		return project_remark;
	}
	public void setProject_remark(String project_remark) {
		this.project_remark = project_remark;
	}
	public String getProcessed_content() {
		return processed_content;
	}
	public void setProcessed_content(String processed_content) {
		this.processed_content = processed_content;
	}
	public String getProject_introduce() {
		return project_introduce;
	}
	public void setProject_introduce(String project_introduce) {
		this.project_introduce = project_introduce;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
}
