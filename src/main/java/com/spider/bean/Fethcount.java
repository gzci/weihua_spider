package com.spider.bean;

import java.sql.Timestamp;

public class Fethcount {
	private String ref_url;
	private Timestamp get_time;
	private Timestamp end_time;
	private int num;
	public String getRef_url() {
		return ref_url;
	}
	public void setRef_url(String ref_url) {
		this.ref_url = ref_url;
	}
	public Timestamp getGet_time() {
		return get_time;
	}
	public void setGet_time(Timestamp get_time) {
		this.get_time = get_time;
	}
	public Timestamp getEnd_time() {
		return end_time;
	}
	public void setEnd_time(Timestamp end_time) {
		this.end_time = end_time;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
	
}
