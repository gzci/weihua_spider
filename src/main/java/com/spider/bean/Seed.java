package com.spider.bean;

import java.sql.Timestamp;

public class Seed {
	private Long id;
	private String ref_url;
	private Timestamp get_time;
	private Timestamp end_time;
	private Long time_skip;
	private String urlcss;
	private String titlecss;// 标题过滤字符串或正则
	private String timecss;
	private String contcss;
	private String source_name;
	private Integer category_id;// 分类ID
	private String briefcss;
	private Integer is_oversea;
	private String hit_tag;// 文章频道
	private Integer flag;
	private Integer url_type;
	private String refcss;
	private String reftimecss;
	private String reftitlecss;
	private String pagingcss;
	private int is_driver;
	private int is_paging;
	
	public int getIs_paging() {
		return is_paging;
	}

	public void setIs_paging(int is_paging) {
		this.is_paging = is_paging;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Long getTime_skip() {
		return time_skip;
	}

	public void setTime_skip(Long time_skip) {
		this.time_skip = time_skip;
	}

	public String getUrlcss() {
		return urlcss;
	}

	public void setUrlcss(String urlcss) {
		this.urlcss = urlcss;
	}

	public String getTitlecss() {
		return titlecss;
	}

	public void setTitlecss(String titlecss) {
		this.titlecss = titlecss;
	}

	public String getTimecss() {
		return timecss;
	}

	public void setTimecss(String timecss) {
		this.timecss = timecss;
	}

	public String getContcss() {
		return contcss;
	}

	public void setContcss(String contcss) {
		this.contcss = contcss;
	}

	public String getSource_name() {
		return source_name;
	}

	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

	public Integer getCategory_id() {
		return category_id;
	}

	public void setCategory_id(Integer category_id) {
		this.category_id = category_id;
	}

	public String getBriefcss() {
		return briefcss;
	}

	public void setBriefcss(String briefcss) {
		this.briefcss = briefcss;
	}

	public Integer getIs_oversea() {
		return is_oversea;
	}

	public void setIs_oversea(Integer is_oversea) {
		this.is_oversea = is_oversea;
	}

	public String getHit_tag() {
		return hit_tag;
	}

	public void setHit_tag(String hit_tag) {
		this.hit_tag = hit_tag;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Integer getUrl_type() {
		return url_type;
	}

	public void setUrl_type(Integer url_type) {
		this.url_type = url_type;
	}

	public String getRefcss() {
		return refcss;
	}

	public void setRefcss(String refcss) {
		this.refcss = refcss;
	}

	public String getReftimecss() {
		return reftimecss;
	}

	public void setReftimecss(String reftimecss) {
		this.reftimecss = reftimecss;
	}

	public String getReftitlecss() {
		return reftitlecss;
	}

	public void setReftitlecss(String reftitlecss) {
		this.reftitlecss = reftitlecss;
	}

	public String getPagingcss() {
		return pagingcss;
	}

	public void setPagingcss(String pagingcss) {
		this.pagingcss = pagingcss;
	}

	public int getIs_driver() {
		return is_driver;
	}

	public void setIs_driver(int is_driver) {
		this.is_driver = is_driver;
	}

}
