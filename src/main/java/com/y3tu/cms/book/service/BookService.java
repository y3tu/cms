package com.y3tu.cms.book.service;

import com.y3tu.cms.book.entity.Book;
import com.baomidou.mybatisplus.extension.service.IService;
import com.y3tu.cms.book.entity.BookContent;
import com.y3tu.cms.book.entity.BookIndex;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 书籍服务
 *
 * @author y3tu
 */
public interface BookService extends IService<Book> {
    /**
     * 查询需要更新的小说数据
     *
     * @param startDate 最新更新时间的起始时间
     * @param limit     查询条数
     * @return 小说集合
     */
    List<Book> queryNeedUpdateBook(Date startDate, int limit);

    /**
     * 批量更新小说最后抓取时间
     *
     * @param books       需要更新的小说集合
     * @param currentDate 当前时间
     */
    void updateCrawlLastTime(List<Book> books, Date currentDate);

    /**
     * 更新小说表，目录表，内容表数据
     *
     * @param book              小说数据
     * @param bookIndexList     目录集合
     * @param bookContentList   内容集合
     * @param existBookIndexMap 已存在的章节Map
     */
    void updateBookAndIndexAndContent(Book book, List<BookIndex> bookIndexList, List<BookContent> bookContentList, Map<Integer, BookIndex> existBookIndexMap);

    /**
     * 保存小说表，目录表，内容表数据
     *
     * @param book            小说数据
     * @param bookIndexList   目录集合
     * @param bookContentList 内容集合
     */
    void saveBookAndIndexAndContent(Book book, List<BookIndex> bookIndexList, List<BookContent> bookContentList);

    /**
     * 删除书籍
     * @param id
     * @return
     */
    boolean delete(long id);
}
