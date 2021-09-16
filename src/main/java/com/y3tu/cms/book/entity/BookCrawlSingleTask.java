package com.y3tu.cms.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 抓取单本小说任务表
 * @TableName book_crawl_single_task
 */
@TableName(value ="book_crawl_single_task")
@Data
public class BookCrawlSingleTask implements Serializable {
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
     * 源站小说ID
     */
    private String sourceBookId;

    /**
     * 任务状态，0：失败，1：成功，2；未执行
     */
    private Boolean taskStatus;

    /**
     * 已经执行次数，最多执行5次
     */
    private Byte excCount;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}