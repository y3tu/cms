package com.y3tu.cms.book.mapper;

import com.y3tu.cms.book.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 书籍Mapper
 *
 * @author y3tu
 */
public interface BookMapper extends BaseMapper<Book> {
    /**
     * 查询需要更新的小说数据
     *
     * @param startDate 最新更新时间的起始时间
     * @param limit     查询条数
     * @return 小说集合
     */
    List<Book> queryNeedUpdateBook(@Param("startDate") Date startDate, @Param("limit") int limit);

    /**
     * 批量更新小说最后抓取时间
     *
     * @param books       需要更新的小说集合
     * @param currentDate 当前时间
     */
    void updateCrawlLastTime(@Param("books") List<Book> books, @Param("currentDate") Date currentDate);
}




