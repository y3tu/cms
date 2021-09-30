package com.y3tu.cms.website.service;

import com.y3tu.cms.website.entity.Weblink;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * weblink服务
 *
 * @author y3tu
 */
public interface WeblinkService extends IService<Weblink> {
    /**
     * 根据分类对网站进行分类
     *
     * @return 已经分好类的网址链接
     */
    Map<String, List<Weblink>> getAllByCategory();

    /**
     * 保存网址链接顺序
     *
     * @param websiteMap
     * @return
     */
    boolean saveWeblinkSort(Map<String, List<Weblink>> websiteMap);
}
