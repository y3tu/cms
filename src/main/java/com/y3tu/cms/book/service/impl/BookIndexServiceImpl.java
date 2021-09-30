package com.y3tu.cms.book.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.y3tu.cms.book.entity.BookIndex;
import com.y3tu.cms.book.service.BookIndexService;
import com.y3tu.cms.book.mapper.BookIndexMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 小说目录服务实现
 *
 * @author y3tu
 */
@Service
public class BookIndexServiceImpl extends ServiceImpl<BookIndexMapper, BookIndex> implements BookIndexService {

    @Autowired
    BookIndexMapper bookIndexMapper;

    @Override
    public Map<Integer, BookIndex> queryExistBookIndexMap(Long bookId) {
        List<BookIndex> bookIndexList = this.lambdaQuery().eq(BookIndex::getBookId, bookId).list();
        if (bookIndexList.size() > 0) {
            return bookIndexList.stream().collect(Collectors.toMap(BookIndex::getIndexNum, Function.identity()));
        }
        return new HashMap<>(0);
    }
}




