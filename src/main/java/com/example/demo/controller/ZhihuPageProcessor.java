package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.IdsDao;
import com.example.demo.entity.Ids;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * @author   czq
 * @Date 2020-06-23 15:09:10    
 */
@Component
public class ZhihuPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000).addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.100 Safari/537.36");;

    public static volatile int book_num=0;
    
    public static volatile int chapter_num =0;
    
    public static volatile int bookId =0;
    
    @Autowired
    private IdsDao idsDao;
    
    @Override
    public void process(Page page) {
    	// 文章页，匹配 https://voice.hupu.com/nba/七位数字.html
    	//https://www.biquge.com/58_58980/3293644.html

        if (page.getUrl().regex("https://www\\.biquge\\.com/25_25184/[0-9]{7}\\.html").match()) {
        	chapter_num++;
        	page.putField("bookId", bookId);
            page.putField("title", page.getHtml().xpath("/html/body/div/div[3]/div[2]/div[2]/h1/text()").toString());
            page.putField("chapterUrl", String.format("%06d",chapter_num));
            page.putField("nextTitle", null);
            page.putField("nextUrl", String.format("%06d",(chapter_num+1)));
            page.putField("lastTitle", null);
            if(chapter_num>1) {
            	page.putField("lastUrl", String.format("%06d",(chapter_num-1)));
        	}else {
        		page.putField("lastUrl", null);
        	}
            page.putField("chapterViews", page.getHtml().xpath("//*[@id=\"content\"]/text()").all().toString().substring(0, 20)+"......");
            page.putField("content",
                    page.getHtml().xpath("//*[@id=\"content\"]/text()").all().toString());
            page.addTargetRequest(
                    page.getHtml().xpath("//*[@id=\"wrapper\"]/div[3]/div[2]/div[2]/div/a[3]/@href").toString());
        }else if(page.getUrl().regex("https://www\\.biquge\\.com/25_25184/").match()) {
        	bookId=getNextid();
        	page.putField("bookViews", "just for show");  
        	///html/body/div/div[6]/div[2]/div[1]/h1/text()
        	page.putField("bookName", page.getHtml().xpath("/html/body/div/div[4]/div[2]/div[1]/h1/text()").toString());
            page.putField("bookId", bookId);
            page.putField("bookUrl", String.format("%06d",bookId));
            page.putField("author", page.getHtml().xpath("/html/body/div/div[4]/div[2]/div[1]/h1/text()").toString());
            
            page.addTargetRequest(
                    page.getHtml().xpath("/html/body/div/div[5]/div/dl/dd[13]/a/@href").toString());
        }
    
    }


    @Override
    public Site getSite() {
    	site = Site.me().setCycleRetryTimes(3000);
        return site;
    }
    
    public int getNextid() {
    	Ids idsDemo=new Ids();
    	idsDemo.setTableName("book");
    	idsDemo.setColName("id");
    	
    	int id=idsDao.selectOne(idsDemo).getId()+1;
    	
    	Ids idsDemo1=new Ids();
    	idsDemo1.setTableName("book");
    	idsDemo1.setColName("id");
    	idsDemo1.setId(id);
    	idsDao.update(idsDemo1);
    	
    	return id;
    }
}