package com.spider.main;

import java.sql.SQLException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import com.spider.bean.Seed;
import com.spider.util.Dao;
import com.spider.util.SleepUtils;
import com.spider.util.XTUtil;
import com.spider.util.process;
@Component
public class root implements Runnable{
	private static final Logger log = LoggerFactory.getLogger(root.class);
	private static long KEY = 0;
	
	@Autowired
	private ApplicationArguments arguments;
	
	@Override
	public void run() {
//		java -jar news.jar --type=root --mid=1
		int machineId = arguments.getOptionValues("mid").stream().mapToInt(Integer::parseInt).findFirst().orElse(-1);
	//	process.close();
		for (;;) {
			Seed seed = Dao.selectSeed();
			if (seed == null) {
				SleepUtils.sleep(1000 * 60);
				continue;
			}
			if (KEY == seed.getId()) {
				continue;
			}
			KEY=seed.getId();
			try {
//				for(Seed o:Dao.selectSeederro()){
//					ContentWEB.parser(o, machineId);
//				}
//				int is_driver=seed.getIs_driver();
				int is_paging=seed.getIs_paging();
				String ref_css=seed.getRefcss();
				if(1==is_paging){
					Contentpaging.parser(seed, machineId);
				}else if(null==ref_css){
					Content.parser(seed, machineId);
				}else{
					ContentRef.parser(seed, machineId);
				}
				
				XTUtil.autoXT();
			} catch (IllegalStateException e) {
				log.error(seed.getId() + "-->" + e.getMessage());
			
			} catch (SQLException e) {
				log.error(seed.getId() + "-->" + e.getMessage());

			}

		}
		
	}

}
