package com.y3tu.cms.book.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 小说类别表
 * book_category
 *
 * @author y3tu
 */
@TableName(value = "book_category")
@Data
public class BookCategory implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 作品方向，M：男频，F：女频
     */
    private String workDirection;

    /**
     * 分类名
     */
    private String name;

    /**
     * 排序
     */
    private int sort;

    /**
     *
     */
    private Long createUserId;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Long updateUserId;

    /**
     *
     */
    private Date updateTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}