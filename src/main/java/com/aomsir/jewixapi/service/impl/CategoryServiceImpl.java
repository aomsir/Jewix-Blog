package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.ArticleMapper;
import com.aomsir.jewixapi.mapper.CategoryMapper;
import com.aomsir.jewixapi.pojo.dto.ArticlePreviewDTO;
import com.aomsir.jewixapi.pojo.dto.CategoryListDTO;
import com.aomsir.jewixapi.pojo.entity.Category;
import com.aomsir.jewixapi.pojo.vo.CategoryAddVo;
import com.aomsir.jewixapi.pojo.vo.CategoryUpdateVo;
import com.aomsir.jewixapi.service.CategoryService;
import com.aomsir.jewixapi.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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
        int count = this.categoryMapper.queryCategoryCountByParentId(0L);

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
        int count = this.categoryMapper.queryCategoryCountByParentId((Long) param.get("parentId"));

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
        Category category = this.categoryMapper.queryCategoryByNameAndParentId(categoryAddVo.getCategoryName(), categoryAddVo.getParentId());

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
        Long categoryId = (Long) param.get("categoryId");
        if (categoryId != null || categoryId <= 0L) {
            throw new CustomerException("分类不存在");
        }

        Category category_1 = this.categoryMapper.queryCategoryId(categoryId);
        if (category_1 == null) {
            throw new CustomerException("分类不存在");
        }

        Long count = this.articleMapper.queryArticleCountByCategoryId(categoryId);
        List<ArticlePreviewDTO> list = null;
        if (count > 0) {
            list = this.categoryMapper.queryArticleListPageByCategoryId(param);
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

        // 查询
        List<Category> categories = this.categoryMapper.queryCategoryListByParentId(0L);
        if (categories == null) {
            return null;
        }
        List<CategoryListDTO> categoryListDTOS = new ArrayList<>();
        for (Category category : categories) {
            CategoryListDTO categoryListDTO = new CategoryListDTO();
            BeanUtils.copyProperties(category,categoryListDTO);
            List<Category> categories_sonList = this.categoryMapper.queryCategoryListByParentId(categoryListDTO.getId());
            categoryListDTO.setSonList(categories_sonList);
            categoryListDTOS.add(categoryListDTO);
        }

        return categoryListDTOS;
    }

    @Override
    @Transactional
    public int updateCategory(CategoryUpdateVo categoryUpdateVo) {
        if (this.categoryMapper.queryCategoryId(categoryUpdateVo.getId()) == null) {
            throw new CustomerException("分类不存在");
        }

        Category category = this.categoryMapper.queryCategoryByNameAndParentId(categoryUpdateVo.getCategoryName(), categoryUpdateVo.getParentId());
        if (category != null) {
            throw new CustomerException("分类名已存在");
        }

        return this.categoryMapper.updateCategory(categoryUpdateVo);
    }


    @Override
    @Transactional
    public int deleteCategories(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            throw new CustomerException("参数携带异常");
        }

        List<Long> trueIds = new ArrayList<>();
        for (Long id : ids) {
            Category category = this.categoryMapper.queryCategoryId(id);
            // 判断是否为父节点
            if (category.getParentId() != 0) {
                // 是否有文章引用
                if (this.categoryMapper.queryCategoryOfArticleCounts(id) == 0) {
                    trueIds.add(id);
                } else {
                    throw new CustomerException("id为" + id + "的分类有文章引用,请重新选择");
                }
            } else {
                // 为父节点
                // 是否有子节点
                List<Category> categories = this.categoryMapper.queryCategoryListByParentId(id);
                if (categories == null || categories.size() == 0) {
                    // 当前分类是否有文章引用
                    if (this.categoryMapper.queryCategoryOfArticleCounts(id) == 0) {
                        trueIds.add(id);
                    } else {
                        throw new CustomerException("id为" + id + "的分类有文章引用,请重新选择");
                    }
                } else {
                    throw new CustomerException("id为" + id + "的分类有子分类,请重新选择");
                }
            }
        }

        return this.categoryMapper.deleteCategories(trueIds);
    }
}
