package com.y3tu.cms.book.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.y3tu.cms.base.PageInfo;
import com.y3tu.cms.book.entity.Book;
import com.y3tu.cms.book.service.BookService;
import com.y3tu.tools.kit.lang.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小说
 *
 * @author y3tu
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    BookService bookService;

    @PostMapping("page")
    public R page(@RequestBody PageInfo<Book> pageInfo) {
        return R.success(bookService.page(pageInfo, new LambdaQueryWrapper<Book>().orderByDesc(Book::getCreateTime)));
    }

    /**
     * 删除小说
     * @param id
     * @return
     */
    public R delete(@RequestBody Long id){
        return R.success();
    }
}
