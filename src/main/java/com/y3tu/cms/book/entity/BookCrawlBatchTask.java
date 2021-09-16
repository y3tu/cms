package com.y3tu.cms.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 批量抓取任务表
 * @TableName book_crawl_batch_task
 */
@TableName(value ="book_crawl_batch_task")
@Data
public class BookCrawlBatchTask implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 爬虫源ID
     */
    private Integer sourceId;

    /**
     * 成功抓取数量
     */
    private Integer crawlCountSuccess;

    /**
     * 目标抓取数量
     */
    private Integer crawlCountTarget;

    /**
     * 任务状态，1：正在运行，0已停止
     */
    private Boolean taskStatus;

    /**
     * 任务开始时间
     */
    private Date startTime;

    /**
     * 任务结束时间
     */
    private Date endTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}