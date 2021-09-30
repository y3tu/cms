package com.y3tu.cms.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.y3tu.cms.website.entity.Weblink;
import com.y3tu.cms.website.entity.WeblinkCategory;
import com.y3tu.cms.website.service.WeblinkCategoryService;
import com.y3tu.cms.website.service.WeblinkService;
import com.y3tu.cms.website.mapper.WeblinkMapper;
import com.y3tu.tools.kit.base.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * weblink服务实现
 *
 * @author y3tu
 */
@Service
public class WeblinkServiceImpl extends ServiceImpl<WeblinkMapper, Weblink> implements WeblinkService {

    @Autowired
    WeblinkCategoryService categoryService;

    @Override
    public Map<String, List<Weblink>> getAllByCategory() {
        List<WeblinkCategory> categoryList = categoryService.lambdaQuery().orderByAsc(WeblinkCategory::getSort).list();
        List<Weblink> weblinkList = this.lambdaQuery().orderByAsc(Weblink::getSort).list();

        Map<String, List<Weblink>> weblinkListMap = new HashMap<>();
        for(WeblinkCategory weblinkCategory:categoryList){
            weblinkListMap.put(weblinkCategory.getId(),new ArrayList<>());
        }
        weblinkList.forEach(weblink -> {
            if (weblinkListMap.get(weblink.getCategoryId()).isEmpty()) {
                List<Weblink> list = new ArrayList<>();
                list.add(weblink);
                weblinkListMap.put(weblink.getCategoryId(), list);
            } else {
                List<Weblink> list = weblinkListMap.get(weblink.getCategoryId());
                list.add(weblink);
                weblinkListMap.put(weblink.getCategoryId(), list);
            }
        });


        Map<String, List<Weblink>> weblinkMap = new HashMap<>();
        for (String key : weblinkListMap.keySet()) {
            for (WeblinkCategory category : categoryList) {
                if (category.getId().equals(key)) {
                    weblinkMap.put(category.getName(), weblinkListMap.get(key));
                }
            }
        }
        return weblinkMap;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveWeblinkSort(Map<String, List<Weblink>> websiteMap) {
        if (ObjectUtil.isNotNull(websiteMap)) {
            Set<String> keySet = websiteMap.keySet();
            for (String key : keySet) {
                //根据分类名称获取分类
                List<WeblinkCategory> weblinkCategoryList = categoryService.lambdaQuery().eq(WeblinkCategory::getName, key).list();
                WeblinkCategory weblinkCategory = weblinkCategoryList.get(0);
                List<Weblink> updateWeblinkList = new ArrayList<>();
                List<Weblink> weblinkList = websiteMap.get(key);
                for (int i = 0; i < weblinkList.size(); i++) {
                    Weblink weblink = weblinkList.get(i);
                    weblink.setSort(i);
                    weblink.setCategoryId(weblinkCategory.getId());
                    updateWeblinkList.add(weblink);
                }

                if (!updateWeblinkList.isEmpty()) {
                    this.updateBatchById(updateWeblinkList);
                }
            }
        }
        return true;
    }
}




