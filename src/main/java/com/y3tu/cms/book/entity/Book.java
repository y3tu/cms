package com.y3tu.cms.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 小说表
 * book
 *
 * @author y3tu
 */
@TableName(value = "book")
@Data
public class Book implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private Long id;

    /**
     * 作品方向，0：男频，1：女频'
     */
    private int workDirection;

    /**
     * 分类ID
     */
    private Integer catId;

    /**
     * 分类名
     */
    private String catName;

    /**
     * 小说封面
     */
    private String picUrl;

    /**
     * 小说名
     */
    private String bookName;

    /**
     * 作者id
     */
    private Long authorId;

    /**
     * 作者名
     */
    private String authorName;

    /**
     * 书籍描述
     */
    private String bookDesc;

    /**
     * 评分，预留字段
     */
    private Double score;

    /**
     * 书籍状态，0：连载中，1：已完结
     */
    private int bookStatus;

    /**
     * 点击量
     */
    private Long visitCount;

    /**
     * 总字数
     */
    private Integer wordCount;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 最新目录ID
     */
    private Long lastIndexId;

    /**
     * 最新目录名
     */
    private String lastIndexName;

    /**
     * 最新目录更新时间
     */
    private Date lastIndexUpdateTime;

    /**
     * 是否收费，1：收费，0：免费
     */
    private Boolean isVip;

    /**
     * 状态，0：入库，1：上架
     */
    private Boolean status;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 爬虫源站ID
     */
    private Integer crawlSourceId;

    /**
     * 抓取的源站小说ID
     */
    private String crawlBookId;

    /**
     * 最后一次的抓取时间
     */
    private Date crawlLastTime;

    /**
     * 是否已停止更新，0：未停止，1：已停止
     */
    private Boolean crawlIsStop;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}