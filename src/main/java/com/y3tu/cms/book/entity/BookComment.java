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
 * @TableName book_comment
 */
@TableName(value ="book_comment")
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
     * 审核状态，0：待审核，1：审核通过，2：审核不通过
     */
    private Boolean auditStatus;

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