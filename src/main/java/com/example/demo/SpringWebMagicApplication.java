package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.task.ZhihuTask;

@SpringBootApplication
@MapperScan(basePackages = "com.example.demo.dao")
public class SpringWebMagicApplication implements CommandLineRunner  {

	@Autowired
    private ZhihuTask zhihuTask;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringWebMagicApplication.class, args);
	}
	
	@Override
    public void run(String... strings) throws Exception {
        // 爬取知乎数据
        zhihuTask.crawl();
    }

}
