package com.spider.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.spider.bean.Seed;
import com.spider.util.Dao;
import com.spider.util.ExcelOperate;
import com.spider.util.MainUtil;

public class addSeed {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		File file = new File("E:\\1.xls");
		String[][] result = ExcelOperate.getData(file, 1);
		int rowLength = result.length;
		for (int i = 0; i < rowLength; i++) {
			String source_name=result[i][0];
			String hit_tag = result[i][1];
			String ref_url = result[i][2];
			if(null==ref_url||"".equals(ref_url)){
				break;
			}
			System.out.println(ref_url);
			long time=12000;
			Seed seed = new Seed();
			seed.setSource_name(source_name);
			seed.setRef_url(ref_url);
			seed.setHit_tag(hit_tag);
			seed.setTime_skip(time);
			Dao.insertSeed(seed);
		}

	}

}
