package com.example.demo.controller;

import java.util.Date;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.dao.BookDao;
import com.example.demo.dao.ChapterDao;
import com.example.demo.dao.IdsDao;
import com.example.demo.entity.Book;
import com.example.demo.entity.Chapter;
import com.example.demo.entity.Ids;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author czq
 * @Date 2020-06-23 15:09:50
 */
@Component
public class ZhihuPipeline implements Pipeline {
	private static final Logger LOGGER = LoggerFactory.getLogger(ZhihuPipeline.class);

	@Autowired
	private BookDao bookDao;

	@Autowired
	private ChapterDao chapterDao;

	@Autowired
	private IdsDao idsDao;

	public void process(ResultItems resultItems, Task task) {
		Integer bookId = resultItems.get("bookId");
		String bookName = resultItems.get("bookName");
		String bookUrl = resultItems.get("bookUrl");
		String bookViews = resultItems.get("bookViews");
		Book book = new Book();
		book.setId(bookId);
		book.setName(bookName);
		book.setUrl(bookUrl);
		book.setViews(bookViews);

		int num = bookDao.selectCount(book);
		if (num == 0) {
			bookDao.insert(book);
		} else {
			String content = resultItems.get("content");
			String lastTitle = resultItems.get("lastTitle");
			String lastUrl = resultItems.get("lastUrl");
			String nextTitle = resultItems.get("nextTitle");
			String nextUrl = resultItems.get("nextUrl");
			String title = resultItems.get("title");
			String chapterurl = resultItems.get("chapterUrl");
			String chapterViews = resultItems.get("chapterViews");

			Chapter chapter = new Chapter();
			chapter.setBookId(getId());
			chapter.setContent(content);
			chapter.setLastTitle(lastTitle);
			chapter.setLastUrl(lastUrl);
			chapter.setNextTitle(nextTitle);
			chapter.setNextUrl(nextUrl);
			chapter.setTitle(title);
			chapter.setUrl(chapterurl);
			chapter.setViews(chapterViews);

			chapterDao.insert(chapter);
		}

	}

	public int getNextid() {
		Ids idsDemo = new Ids();
		idsDemo.setTableName("book");
		idsDemo.setColName("id");
		return idsDao.selectOne(idsDemo).getId() + 1;
	}

	public int getId() {
		Ids idsDemo = new Ids();
		idsDemo.setTableName("book");
		idsDemo.setColName("id");
		return idsDao.selectOne(idsDemo).getId();
	}
}