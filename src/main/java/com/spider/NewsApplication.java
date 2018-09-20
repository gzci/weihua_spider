package com.spider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.spider.main.Boot;
import com.spider.main.root;

@SpringBootApplication
public class NewsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(NewsApplication.class, args);
		context.getBean(Boot.class).run();
	}

}
