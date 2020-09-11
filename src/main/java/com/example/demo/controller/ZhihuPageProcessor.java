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
//    	https://m.imitui.com/manhua/haizeiwang/225814.html
//        if (page.getUrl().regex("https://voice\\.hupu\\.com/nba/[0-9]{7}\\.html").match()) {
//            page.putField("Title", page.getHtml().xpath("//html/body/div[4]/div[1]/div[1]/h1/text()").toString());
//            page.putField("Content",
//                    page.getHtml().xpath("//html/body/div[4]/div[1]/div[2]/div/div[2]/p/text()").all().toString());
//        }
//        // 列表页
//        else {
//            // 文章url
//            page.addTargetRequests(
//                    page.getHtml().xpath("//html/body/div[3]/div[1]/div[2]/ul/li/div[1]/h4/a/@href").all());
//            // 翻页url
//            page.addTargetRequests(
//                    page.getHtml().xpath("//html/body/div[3]/div[1]/div[3]/a[@class='page-btn-prev']/@href").all());
//        }
    
//    		https://m.imitui.com/manhua/haizeiwang/2	25814.html
    if (page.getUrl().regex("https://manhua\\.fzdm\\.com/2/*").match()) {
    	
        page.putField("Title", page.getHtml().xpath("//html/body/div[3]/div[1]/h1/text()").toString());
        String imgurl=page.getHtml().xpath("//html/body/div[3]/div[1]/div[1]/a/img").css("img", "src").toString();
        page.putField("Content",imgurl);
        if(imgurl!=null&&imgurl.startsWith("http")) {
        	page.addTargetRequests(
                    page.getHtml().xpath("/html/body/div[3]/div[1]/div[2]/a[8]/@href").all());
        }else {
        	page.setDownloadSuccess(false);
        }
     // 翻页url
        
    }
    // 列表页
//    else {
//        // 文章url
//        page.addTargetRequest("https://m.imitui.com"+
//                page.getHtml().xpath("//html/body/div[1]/div[1]/div[3]/div[2]/p[5]/a[2]/@href"));
//        
//    }
    }
    
//    protected Page handleResponse(Request request, String charset, HttpResponse httpResponse, Task task) throws IOException {
//        Page page = new Page();
//        if (httpResponse.getStatusLine().getStatusCode() != HttpConstant.StatusCode.CODE_200) {
//            page.setDownloadSuccess(false);
//        } else {
//            byte[] bytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());
//            String contentType = httpResponse.getEntity().getContentType() == null ? "" : httpResponse.getEntity().getContentType().getValue();
//            page.setBytes(bytes);
//            if (!request.isBinaryContent()){
//                if (charset == null) {
//                    charset = getHtmlCharset(contentType, bytes);
//                }
//                page.setCharset(charset);
//                page.setRawText(new String(bytes, charset));
//            }
//            page.setUrl(new PlainText(request.getUrl()));
//            page.setRequest(request);
//            page.setStatusCode(httpResponse.getStatusLine().getStatusCode());
//            page.setDownloadSuccess(true);
//            if (responseHeader) {
//                page.setHeaders(HttpClientUtils.convertHeaders(httpResponse.getAllHeaders()));
//            }
//        }
//        return page;
//    }


    @Override
    public Site getSite() {
    	site = Site.me().setCycleRetryTimes(3000);
        return site;
    }
}