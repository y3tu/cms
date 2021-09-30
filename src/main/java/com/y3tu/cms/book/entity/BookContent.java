package com.y3tu.cms.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 小说内容表
 * book_content
 *
 * @author y3tu
 */
@TableName(value = "book_content")
@Data
public class BookContent implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 目录ID
     */
    private Long indexId;

    /**
     * 小说章节内容
     */
    private String content;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}