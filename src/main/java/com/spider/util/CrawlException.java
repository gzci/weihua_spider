package com.spider.util;

public class CrawlException extends Exception {
	private static final long serialVersionUID = 9205600555581610069L;

	public CrawlException() {
		super();
	}
	public CrawlException(String message) {
		super(message);
	}
	public CrawlException(String message, Throwable cause) {
		super(message, cause);
	}
	public CrawlException(Throwable cause) {
		super(cause);
	}

}
