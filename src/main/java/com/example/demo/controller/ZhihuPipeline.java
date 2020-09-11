package com.example.demo.controller;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.CrawlerMapper;
import com.example.demo.entity.CmsContentPO;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author   czq
 * @Date 2020-06-23 15:09:50    
 */
@Component
public class ZhihuPipeline implements Pipeline {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZhihuPipeline.class);

    @Autowired
    private CrawlerMapper crawlerMapper;

    public void process(ResultItems resultItems, Task task) {
        String title = resultItems.get("Title");
        String answer = resultItems.get("Content");

        CmsContentPO contentPO = new CmsContentPO();
        contentPO.setContentId(UUID.randomUUID().toString());
        contentPO.setTitle(title);
        contentPO.setReleaseDate(new Date());
        contentPO.setContent(answer); 

        try {
            boolean success = crawlerMapper.addCmsContent(contentPO) > 0;
            LOGGER.info("保存知乎文章成功：{}", title);
        } catch (Exception ex) {
            LOGGER.error("保存知乎文章失败", ex);
        }
    }
}