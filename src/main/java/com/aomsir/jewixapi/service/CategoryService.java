package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.utils.PageUtils;

import java.util.Map;

public interface CategoryService {
    PageUtils searchCategoryParentListByPage(Map<String, Object> param);

    PageUtils searchCategorySonListPageByPatentId(Map<String, Object> param);
}
