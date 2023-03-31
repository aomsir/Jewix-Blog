package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.dto.CategoryListDTO;
import com.aomsir.jewixapi.pojo.vo.CategoryAddVo;
import com.aomsir.jewixapi.utils.PageUtils;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    PageUtils searchCategoryParentListByPage(Map<String, Object> param);

    PageUtils searchCategorySonListPageByPatentId(Map<String, Object> param);

    int addCategory(CategoryAddVo categoryAddVo);

    PageUtils searchArticlePageByCategoryName(Map<String, Object> param);

    List<CategoryListDTO> searchCategoryList();

}
