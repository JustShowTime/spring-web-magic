package com.example.demo.controller;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.PlainText;
import us.codecraft.webmagic.utils.HttpConstant;

/**
 * @author   czq
 * @Date 2020-06-23 15:09:10    
 */
@Component
public class ZhihuPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");;

    @Override
    public void process(Page page) {
    	// 文章页，匹配 https://voice.hupu.com/nba/七位数字.html
    	//https://www.biquge.com/58_58980/3293644.html

        if (page.getUrl().regex("https://www\\.biquge\\.com/25_25184/[0-9]{7}\\.html").match()) {
            page.putField("Title", page.getHtml().xpath("/html/body/div/div[3]/div[2]/div[2]/h1/text()").toString());
            page.putField("Content",
                    page.getHtml().xpath("//*[@id=\"content\"]/text()").all().toString());
            page.addTargetRequest(
                    page.getHtml().xpath("//*[@id=\"wrapper\"]/div[3]/div[2]/div[2]/div/a[3]/@href").toString());
        }
    
    }


    @Override
    public Site getSite() {
    	site = Site.me().setCycleRetryTimes(3000);
        return site;
    }
}