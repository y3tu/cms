package com.y3tu.cms.book.service;

import com.y3tu.cms.book.crawl.RuleBean;
import com.y3tu.cms.book.entity.BookCrawlSingleTask;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 单个爬虫任务
 *
 * @author y3tu
 */
public interface BookCrawlSingleTaskService extends IService<BookCrawlSingleTask> {
    /**
     * 采集并保存小说
     *
     * @param sourceId     源ID
     * @param sourceBookId 小说ID
     * @param catId        分类ID
     * @param ruleBean     采集规则
     * @return true:成功，false:失败
     */
    boolean parseBookAndSave(Integer sourceId, String sourceBookId, int catId, RuleBean ruleBean);
}
