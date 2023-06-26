package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.dto.CategoryListDTO;
import com.aomsir.jewixapi.pojo.vo.CategoryAddVo;
import com.aomsir.jewixapi.pojo.vo.CategoryUpdateVo;
import com.aomsir.jewixapi.util.PageUtils;

import java.util.List;
import java.util.Map;


/**
 * @Author: Aomsir
 * @Date: 2023/6/26
 * @Description: 分类业务接口
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface CategoryService {

    /**
     * 分页查询父分类列表
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchCategoryParentListByPage(Map<String, Object> param);


    /**
     * 根据父分类id分页查询分类列表
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchCategorySonListPageByPatentId(Map<String, Object> param);


    /**
     * 新增分类
     * @param categoryAddVo 封装对象
     * @return 新增所影响的行数
     */
    int addCategory(CategoryAddVo categoryAddVo);


    /**
     * 根据分类名分页查询文章列表
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchArticlePageByCategoryName(Map<String, Object> param);


    /**
     * 查询分类列表
     * @return 分类列表
     */
    List<CategoryListDTO> searchCategoryList();


    /**
     * 更新分类
     * @param categoryUpdateVo 封装对象
     * @return 更新分类所影响的行数
     */
    int updateCategory(CategoryUpdateVo categoryUpdateVo);


    /**
     * 删除分类
     * @param ids 分类id列表
     * @return 删除所影响的行数
     */
    int deleteCategories(List<Long> ids);
}
