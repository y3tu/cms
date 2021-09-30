package com.y3tu.cms.book.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.y3tu.cms.book.crawl.CrawlParser;
import com.y3tu.cms.book.crawl.RuleBean;
import com.y3tu.cms.book.entity.Book;
import com.y3tu.cms.book.entity.BookCrawlSingleTask;
import com.y3tu.cms.book.service.BookCategoryService;
import com.y3tu.cms.book.service.BookCrawlSingleTaskService;
import com.y3tu.cms.book.mapper.BookCrawlSingleTaskMapper;
import com.y3tu.cms.book.service.BookService;
import com.y3tu.cms.exception.BookException;
import com.y3tu.tools.kit.base.IdUtil;
import com.y3tu.tools.kit.exception.ToolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 单个爬虫任务实现
 *
 * @author y3tu
 */
@Service
public class BookCrawlSingleTaskServiceImpl extends ServiceImpl<BookCrawlSingleTaskMapper, BookCrawlSingleTask> implements BookCrawlSingleTaskService {

    @Autowired
    BookService bookService;
    @Autowired
    BookCategoryService bookCategoryService;

    @Override
    public boolean parseBookAndSave(Integer sourceId, String sourceBookId, int catId, RuleBean ruleBean) {
        final AtomicBoolean parseResult = new AtomicBoolean(false);
        CrawlParser.parseBook(sourceBookId, ruleBean, book -> {
            if (book.getBookName() == null || book.getAuthorName() == null) {
                throw new BookException("没有找到小说，请检查！");
            }

            //这里只做新书入库，查询是否存在这本书
            List<Book> bookList = bookService.lambdaQuery().eq(Book::getBookName, book.getBookName()).eq(Book::getAuthorName, book.getAuthorName()).list();
            if (bookList.isEmpty()) {
                //如果该小说不存在，则可以解析入库，但是标记该小说正在入库，30分钟之后才允许再次入库
                book.setCatId(catId);
                //设置分类名
                book.setCatName(bookCategoryService.getById(catId).getName());
                if (catId == 7) {
                    //女频
                    book.setWorkDirection((byte) 1);
                } else {
                    //男频
                    book.setWorkDirection((byte) 0);
                }

                book.setCrawlBookId(sourceBookId);
                book.setCrawlSourceId(sourceId);
                book.setCrawlLastTime(new Date());
                book.setId(IdUtil.nextId());
                //解析章节目录
                CrawlParser.parseBookIndexAndContent(sourceBookId, book, ruleBean, new HashMap<>(0), chapter -> {
                    bookService.saveBookAndIndexAndContent(book, chapter.getBookIndexList(), chapter.getBookContentList());
                });
            } else {
                //只更新书籍的爬虫相关字段
                for (Book bookUpdate : bookList) {
                    bookUpdate.setCrawlBookId(sourceBookId);
                    bookUpdate.setCrawlSourceId(sourceId);
                    bookService.updateById(bookUpdate);
                }
            }
            parseResult.set(true);
        });
        return parseResult.get();
    }
}




