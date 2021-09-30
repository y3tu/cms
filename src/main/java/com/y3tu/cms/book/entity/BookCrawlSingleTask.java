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
 *
 * @author y3tu
 * book_crawl_single_task
 */
@TableName(value = "book_crawl_single_task")
@Data
public class BookCrawlSingleTask implements Serializable {

    public static String TASK_STATUS_ERROR = "00E";
    public static String TASK_STATUS_SUCCESS = "00A";
    public static String TASK_STATUS_WAIT = "00W";

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
     * 爬虫源名
     */
    private String sourceName;

    /**
     * 源站小说ID
     */
    private String sourceBookId;

    /**
     * 分类ID
     */
    private Integer catId;

    /**
     * 爬取的小说名
     */
    private String bookName;

    /**
     * 爬取的小说作者名
     */
    private String authorName;

    /**
     * 任务状态，OOE：失败，00A：成功，00W；未执行
     */
    private String taskStatus;

    /**
     * 已经执行次数，最多执行5次
     */
    private int excCount;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 失败原因
     */
    String errorMsg;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}