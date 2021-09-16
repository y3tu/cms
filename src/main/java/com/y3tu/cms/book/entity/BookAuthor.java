package com.y3tu.cms.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 作者表
 * book_author
 *
 * @author y3tu
 */
@TableName(value = "book_author")
@Data
public class BookAuthor implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 邀请码
     */
    private String inviteCode;

    /**
     * 笔名
     */
    private String penName;

    /**
     * 手机号码
     */
    private String telPhone;

    /**
     * QQ或微信账号
     */
    private String chatAccount;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 作品方向，0：男频，1：女频
     */
    private Byte workDirection;

    /**
     * 0：待审核，1：审核通过，正常，2：审核不通过
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 申请人ID
     */
    private Long createUserId;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人ID
     */
    private Long updateUserId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}