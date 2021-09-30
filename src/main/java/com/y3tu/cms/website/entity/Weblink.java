package com.y3tu.cms.website.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 网站
 * <p>
 * weblink
 *
 * @author y3tu
 */
@TableName(value = "weblink")
@Data
public class Weblink implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private String id;

    /**
     * 网站名称
     */
    private String name;

    /**
     * 网站url
     */
    private String url;

    /**
     * 网站图标url
     */
    private String iconUrl;

    /**
     * 网站分类
     */
    private String categoryId;

    /**
     * 描述
     */
    private String description;
    /**
     * 排列顺序
     */
    int sort;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}