package com.y3tu.cms.book.crawl;

import com.y3tu.cms.book.entity.Book;

/**
 * 小说章节处理器
 *
 * @author y3tu
 */
@FunctionalInterface
public interface CrawlBookChapterHandler {
    /**
     * 处理章节
     *
     * @param chapterBean 章节
     */
    void handle(ChapterBean chapterBean);
}
