package com.y3tu.cms.book.crawl;

import com.y3tu.cms.book.entity.Book;

/**
 * 小说处理器
 *
 * @author y3tu
 */
@FunctionalInterface
public interface CrawlBookHandler {
    /**
     * 处理书籍
     *
     * @param book 书籍
     */
    void handle(Book book);
}
