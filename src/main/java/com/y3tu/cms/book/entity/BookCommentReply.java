package com.y3tu.cms.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 小说评论回复表
 * book_comment_reply
 *
 * @author y3tu
 */
@TableName(value = "book_comment_reply")
@Data
public class BookCommentReply implements Serializable {
    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 评论ID
     */
    private Long commentId;

    /**
     * 回复内容
     */
    private String replyContent;

    /**
     * 00W：待审核，00A：审核通过，正常，00X：审核不通过
     */
    private String auditStatus;

    /**
     * 回复时间
     */
    private Date createTime;

    /**
     * 回复用户ID
     */
    private Long createUserId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}