package com.spider.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;

public class OssTools {

	static final String accessKeyId = "WfsLZAlfEO9JlwzW";
	static final String accessKeySecret = "tq0HcHl7c5esl9qy1Z6Hv646OfpeOV";
	static final String backer = "rcc-image";

	public static String uploadBytes(byte[] bytes, String key,String content_type) throws IOException {
		String endpoint = "http://oss-cn-beijing.aliyuncs.com";
		// 创建OSSClient实例
		OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		ObjectMetadata metadata=new ObjectMetadata();
		metadata.setContentType(content_type);
		ossClient.putObject(backer, key, new ByteArrayInputStream(bytes), metadata);
		OSSObject obj = ossClient.getObject(backer, key);
	
	 
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		obj.getObjectContent();
		IOUtils.copy(obj.getObjectContent(), baos);
		if (!Arrays.equals(bytes, baos.toByteArray())) {
			throw new IOException("upload err");
		}
		// 关闭ossClient
		ossClient.shutdown();
		return "http://rcc-image.oss-cn-beijing.aliyuncs.com/" + key;

	}
//	private static byte[] toByteArray(InputStream in) throws IOException {
//		 
//	    ByteArrayOutputStream out = new ByteArrayOutputStream();
//	    byte[] buffer = new byte[1024 * 4];
//	    int n = 0;
//	    while ((n = in.read(buffer)) != -1) {
//	        out.write(buffer, 0, n);
//	    }
//	    return out.toByteArray();
//	}
//	private static byte[] InputStream2ByteArray(String filePath) throws IOException {
//		 
//	    InputStream in = new FileInputStream(filePath);
//	    byte[] data = toByteArray(in);
//	    in.close();
//	 
//	    return data;
//	}
	public static void main(String[] args) throws IOException{
	}
}
