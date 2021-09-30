package com.y3tu.cms.book.crawl;

import lombok.Data;

import java.util.Map;

/**
 * 爬虫解析规则bean
 *
 * @author y3tu
 */
@Data
public class RuleBean {

    /**
     * 小说更新列表url
     */
    private String updateBookListUrl;

    /**
     * 分类列表页URL规则
     */
    private String bookListUrl;
    /**
     * 分类
     */
    private Map<String, String> catIdRule;
    /**
     * 小说状态 0:连载 1：全本
     */
    private Map<String, Byte> bookStatusRule;
    /**
     * bookId正则
     */
    private String bookIdPatten;
    /**
     * 分页正则
     */
    private String pagePatten;
    /**
     * 总页数正则
     */
    private String totalPagePatten;
    /**
     * 小说详情url
     */
    private String bookDetailUrl;
    /**
     * 小说名正则
     */
    private String bookNamePatten;
    /**
     * 作者正则
     */
    private String authorNamePatten;
    /**
     * 封面图片正则
     */
    private String picUrlPatten;
    /**
     * 封面图片url前缀
     */
    private String picUrlPrefix;
    /**
     * 小说更新状态正则
     */
    private String statusPatten;
    /**
     * 小说评分正则
     */
    private String scorePatten;
    /**
     * 小说访问次数正则
     */
    private String visitCountPatten;
    /**
     * 小说简介开始
     */
    private String descStart;
    /**
     * 小说简介结束
     */
    private String descEnd;
    /**
     * 更新时间正则
     */
    private String updateTimePatten;
    /**
     * 更新时间格式
     */
    private String updateTimeFormatPatten;
    /**
     * 小说目录url
     */
    private String bookIndexUrl;
    /**
     * 小说目录开始
     */
    private String bookIndexStart;
    /**
     * 小说目录ID正则
     */
    private String indexIdPatten;
    /**
     * 小说目录名称正则
     */
    private String indexNamePatten;
    /**
     * 小说具体内容url
     */
    private String bookContentUrl;
    /**
     * 小说内容开始
     */
    private String contentStart;
    /**
     * 小说内容结束
     */
    private String contentEnd;


}
