package com.y3tu.cms.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 爬虫源表
 * book_crawl_source
 *
 * @author y3tu
 */
@TableName(value = "book_crawl_source")
@Data
public class BookCrawlSource implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 源站名
     */
    private String sourceName;

    /**
     * 爬取规则（json串）
     */
    private String crawlRule;

    /**
     * 爬虫源状态，00X：关闭，00A：开启
     */
    private String sourceStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}