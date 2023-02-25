package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.mapper.CategoryMapper;
import com.aomsir.jewixapi.pojo.entity.Category;
import com.aomsir.jewixapi.service.CategoryService;
import com.aomsir.jewixapi.utils.PageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/2/25
 * @Description: 分页业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public PageUtils searchCategoryParentListByPage(Map<String, Object> param) {
        int count = this.categoryMapper.queryCategoryCountByParentId(0);

        ArrayList<Category> list = null;
        if (count > 0) {
            list = this.categoryMapper.queryCategoryListPageByParentId(param);
        } else {
            list = new ArrayList<>();
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list,count,start,length);
        return pageUtils;
    }

    @Override
    public PageUtils searchCategorySonListPageByPatentId(Map<String, Object> param) {
        int count = this.categoryMapper.queryCategoryCountByParentId((Integer) param.get("parentId"));

        ArrayList<Category> list = null;
        if (count > 0) {
            list = this.categoryMapper.queryCategoryListPageByParentId(param);
        } else {
            list = new ArrayList<>();
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list,count,start,length);
        return pageUtils;
    }
}
