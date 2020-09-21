package com.example.demo.task;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.controller.ZhihuPageProcessor;
import com.example.demo.controller.ZhihuPipeline;

import us.codecraft.webmagic.Spider;

/**
 * @author   czq
 * @Date 2020-06-23 15:11:29    
 */
@Component
public class ZhihuTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZhihuPipeline.class);

    @Autowired
    private ZhihuPipeline zhihuPipeline;

    @Autowired
    private ZhihuPageProcessor zhihuPageProcessor;

    private ScheduledExecutorService timer = Executors.newSingleThreadScheduledExecutor();

    public void crawl() {
        // 定时任务，每10分钟爬取一次
        timer.scheduleWithFixedDelay(() -> {
            Thread.currentThread().setName("zhihuCrawlerThread");

            try {
                Spider.create(zhihuPageProcessor)
                        // 从https://www.zhihu.com/explore开始抓
//                        .addUrl("https://voice.hupu.com/nba/1")
//                        https://m.imitui.com/manhua/haizeiwang/
                .addUrl("https://www.biquge.com/25_25184/1589038.html")
                        // 抓取到的数据存数据库
                        .addPipeline(zhihuPipeline)
                        // 开启2个线程抓取
                        .thread(1)
                        // 异步启动爬虫
                        .start();
            } catch (Exception ex) {
                LOGGER.error("定时抓取知乎数据线程执行异常", ex);
            }
        }, 0, 10, TimeUnit.MINUTES);
    }
}