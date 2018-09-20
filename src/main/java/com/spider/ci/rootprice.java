package com.spider.ci;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class rootprice implements Runnable{
	private static final Logger log = LoggerFactory.getLogger(rootprice.class);
	@Override
	public void run() {
		log.info("每天执行一次");
		pvinfolink.main();
		pvinsights.main();
		energytrend.main();
		spv21spv.main();
		try {
			wxprice.main();
		} catch (SQLException e) {
			log.error(e.getMessage());
		}

	}

}
