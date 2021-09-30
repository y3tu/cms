package com.y3tu.cms.book.crawl;

import com.y3tu.cms.book.entity.BookContent;
import com.y3tu.cms.book.entity.BookIndex;
import lombok.Data;

import java.util.List;

/**
 * 章节数据封装bean
 *
 * @author y3tu
 */
@Data
public class ChapterBean {

    /**
     * 章节索引集合
     */
    List<BookIndex> bookIndexList;

    /**
     * 章节内容集合
     */
    List<BookContent> bookContentList;
}
