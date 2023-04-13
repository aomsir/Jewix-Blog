package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.ArticleMapper;
import com.aomsir.jewixapi.mapper.CategoryMapper;
import com.aomsir.jewixapi.pojo.dto.ArticlePreviewDTO;
import com.aomsir.jewixapi.pojo.dto.CategoryListDTO;
import com.aomsir.jewixapi.pojo.entity.Category;
import com.aomsir.jewixapi.pojo.vo.CategoryAddVo;
import com.aomsir.jewixapi.service.CategoryService;
import com.aomsir.jewixapi.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);
    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private ArticleMapper articleMapper;

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


    @Override
    @Transactional
    public int addCategory(CategoryAddVo categoryAddVo) {
        //  根据子分类名与父类id去查询是否已经存在
        Category category = this.categoryMapper.queryCategoryByNameAndParentId(categoryAddVo.getCategoryName(),categoryAddVo.getParentId());

        // 存在则无法进行添加(一级分类与二级分类均适用)
        if (category != null) {
            throw new CustomerException("分类已存在嗷!");
        }

        // 非一级分类的情况
        if (categoryAddVo.getParentId() != 0) {
            Category category_1 = this.categoryMapper.queryCategoryByParentId(categoryAddVo.getParentId());

            if (category_1 == null) {
                throw new CustomerException("一级分类不存在嗷,请重新选择!");
            }

            if (category_1.getParentId() != 0) {
                throw new CustomerException("选择的分类不是一级分类嗷!");
            }

            // 再次保证a子分类可以出现在多个父分类下
            List<Category> list = this.categoryMapper.queryCategoryListByParentId(categoryAddVo.getParentId());
            for (Category category1 : list) {
                if (category1.getCategoryName().equals(categoryAddVo.getCategoryName())) {
                    throw new CustomerException(category_1.getCategoryName() + "分类下已有 "+categoryAddVo.getCategoryName()+" 分类");
                }
            }
        }

        Category category_2 = new Category();
        category_2.setCategoryName(categoryAddVo.getCategoryName());
        category_2.setParentId(categoryAddVo.getParentId());
        category_2.setCreateTime(new Date());
        category_2.setUpdateTime(new Date());

        return this.categoryMapper.insertCategory(category_2);
    }


    @Override
    public PageUtils searchArticlePageByCategoryName(Map<String, Object> param) {
        Category category = this.categoryMapper.queryCategoryByName((String) param.get("categoryName"));
        if (category == null) {
            throw new CustomerException("分类不存在嗷!");
        }

        Long count = this.articleMapper.queryArticleCountByCategoryName((String) param.get("categoryName"));
        List<ArticlePreviewDTO> list = null;
        if (count > 0) {
            list = this.categoryMapper.queryArticleListPageByCategoryName(param);
        } else {
            list = new ArrayList<>();
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list,count,start,length);
        return pageUtils;
    }

    @Override
    public List<CategoryListDTO> searchCategoryList() {
        return this.categoryMapper.queryCategoryList();
    }
}
