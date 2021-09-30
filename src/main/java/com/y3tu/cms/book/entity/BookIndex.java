package com.y3tu.cms.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 小说目录表
 * book_index
 *
 * @author y3tu
 */
@TableName(value = "book_index")
@Data
public class BookIndex implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 小说ID
     */
    private Long bookId;

    /**
     * 目录号
     */
    private Integer indexNum;

    /**
     * 目录名
     */
    private String indexName;

    /**
     * 字数
     */
    private Integer wordCount;

    /**
     * 是否收费 00A：是，00X：否
     */
    private String isVip;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}