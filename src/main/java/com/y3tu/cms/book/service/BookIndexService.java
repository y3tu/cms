package com.y3tu.cms.book.service;

import com.y3tu.cms.book.entity.BookIndex;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * 小说目录服务接口
 *
 * @author y3tu
 */
public interface BookIndexService extends IService<BookIndex> {
    /**
     * 查询已存在的章节
     *
     * @param bookId 小说ID
     * @return 章节号和章节数据对映射map
     */
    Map<Integer, BookIndex> queryExistBookIndexMap(Long bookId);
}
