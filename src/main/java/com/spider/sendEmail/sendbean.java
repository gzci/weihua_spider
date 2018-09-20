package com.spider.sendEmail;

public class sendbean {
	private String type;
	private String tablecheck;
	private String date;
	private long count;
	private String is_not;
	public String getIs_not() {
		return is_not;
	}
	public void setIs_not(String is_not) {
		this.is_not = is_not;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTablecheck() {
		return tablecheck;
	}
	public void setTablecheck(String tablecheck) {
		this.tablecheck = tablecheck;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return ""+type + ": " + tablecheck + "  " + date + " " + count + "条";
	}
	
	
	
	
}
