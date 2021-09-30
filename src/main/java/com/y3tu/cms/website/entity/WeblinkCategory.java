package com.y3tu.cms.website.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 网站分类
 * weblink_category
 *
 * @author y3tu
 */
@TableName(value = "weblink_category")
@Data
public class WeblinkCategory implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 分类名
     */
    private String name;

    /**
     * 分类描述
     */
    private String description;
    /**
     * 排列顺序
     */
    int sort;
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}