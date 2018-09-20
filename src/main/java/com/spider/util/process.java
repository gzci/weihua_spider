package com.spider.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;

public class process {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	public static void close() {
		try {
			if (SystemUtils.IS_OS_LINUX) {
				if (!new File("/usr/bin/killwebdriver").isFile()) {
					FileUtils.writeByteArrayToFile(new File("/usr/bin/killwebdriver"), IOUtils
							.toByteArray(new URL("http://wwh-office.oss-cn-beijing.aliyuncs.com/killwebdriver")));
				}
				command("chmod a+x /usr/bin/phantomjs");
				command("chmod a+x /usr/bin/killwebdriver");
				command("/usr/bin/killwebdriver phantomjs");
				command("/usr/bin/killwebdriver chromedriver");
			} else if (SystemUtils.IS_OS_WINDOWS) {
				
				command("taskkill /F /im " + "chromedriver.exe");
				command("taskkill /F /im " + "chrome.exe");
			} else {
				throw new IllegalArgumentException("未能识别操作系统.");
			}
		} catch (Exception e) {
		}
	}
	 public static void command(String command) {
	        try {
	            Runtime.getRuntime().exec(command);
//	            process.waitFor();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
}
