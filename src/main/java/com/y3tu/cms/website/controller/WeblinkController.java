package com.y3tu.cms.website.controller;

import com.y3tu.cms.website.entity.Weblink;
import com.y3tu.cms.website.service.WeblinkService;
import com.y3tu.tools.kit.lang.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 网页链接
 *
 * @author y3tu
 */
@RestController
@RequestMapping("/weblink")
public class WeblinkController {

    @Autowired
    WeblinkService weblinkService;

    /**
     * 获取所有的网址链接并分类
     *
     * @return
     */
    @GetMapping("getAllByCategory")
    public R getAllByCategory() {
        Map<String, List<Weblink>> websiteMap = weblinkService.getAllByCategory();
        return R.success(websiteMap);
    }

    /**
     * 保存网址链接的排列顺序
     *
     * @return
     */
    @PostMapping("saveWeblinkSort")
    public R saveWeblinkSort(@RequestBody Map<String, List<Weblink>> websiteMap) {
        boolean flag = weblinkService.saveWeblinkSort(websiteMap);
        return R.success(flag);
    }
}
