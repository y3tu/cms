package com.y3tu.cms.book.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.y3tu.cms.book.entity.Book;
import com.y3tu.cms.book.entity.BookContent;
import com.y3tu.cms.book.entity.BookIndex;
import com.y3tu.cms.book.mapper.BookContentMapper;
import com.y3tu.cms.book.mapper.BookIndexMapper;
import com.y3tu.cms.book.service.BookContentService;
import com.y3tu.cms.book.service.BookIndexService;
import com.y3tu.cms.book.service.BookService;
import com.y3tu.cms.book.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 书籍服务实现类
 *
 * @author y3tu
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    BookMapper bookMapper;
    @Autowired
    BookIndexService bookIndexService;
    @Autowired
    BookContentService bookContentService;

    @Override
    public List<Book> queryNeedUpdateBook(Date startDate, int limit) {
        return bookMapper.queryNeedUpdateBook(startDate, limit);
    }

    @Override
    public void updateCrawlLastTime(List<Book> books, Date currentDate) {
        bookMapper.updateCrawlLastTime(books, currentDate);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBookAndIndexAndContent(Book book, List<BookIndex> bookIndexList, List<BookContent> bookContentList, Map<Integer, BookIndex> existBookIndexMap) {
        for (int i = 0; i < bookIndexList.size(); i++) {
            BookIndex bookIndex = bookIndexList.get(i);
            BookContent bookContent = bookContentList.get(i);
            if (!existBookIndexMap.containsKey(bookIndex.getIndexNum())) {
                //插入
                bookIndexService.save(bookIndex);
                bookContentService.save(bookContent);
            } else {
                //更新
                bookIndexService.updateById(bookIndex);
                bookContentService.updateById(bookContent);
            }
        }

        //更新小说主表
        book.setBookName(null);
        book.setAuthorName(null);
        book.setVisitCount(null);
        bookMapper.updateById(book);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveBookAndIndexAndContent(Book book, List<BookIndex> bookIndexList, List<BookContent> bookContentList) {
        if (bookIndexList.size() > 0) {
            //保存小说主表
            book.setCreateTime(new Date());
            bookMapper.insert(book);

            bookIndexService.saveBatch(bookIndexList);
            bookContentService.saveBatch(bookContentList);

        }
    }

}




