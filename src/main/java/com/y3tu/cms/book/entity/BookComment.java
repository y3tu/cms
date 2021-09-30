package com.y3tu.cms.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 小说评论表
 * book_comment
 *
 * @author y3tu
 */
@TableName(value = "book_comment")
@Data
public class BookComment implements Serializable {
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
     * 评价内容
     */
    private String commentContent;

    /**
     * 回复数量
     */
    private Integer replyCount;

    /**
     * 00W：待审核，00A：审核通过，正常，00X：审核不通过
     */
    private String auditStatus;

    /**
     * 评价时间
     */
    private Date createTime;

    /**
     * 评价人
     */
    private Long createUserId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}