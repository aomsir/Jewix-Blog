package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.pojo.dto.CategoryListDTO;
import com.aomsir.jewixapi.pojo.vo.ArticleCategoryVo;
import com.aomsir.jewixapi.pojo.vo.CategoryAddVo;
import com.aomsir.jewixapi.pojo.vo.CategoryParentPageVo;
import com.aomsir.jewixapi.pojo.vo.CategorySonListById;
import com.aomsir.jewixapi.service.CategoryService;
import com.aomsir.jewixapi.utils.PageUtils;
import com.aomsir.jewixapi.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/2/25
 * @Description: 分类控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Api(tags = "分类控制器")
@RestController
public class CategoryController {

    @Resource
    private CategoryService categoryService;


    /**
     * 分页查询一级分类列表
     * @param categoryParentPageVo 分页查询一级分类列表VO对象
     * @return 一级分类列表
     */
    @ApiOperation(value = "分页查询一级分类列表")
    @GetMapping("/categories/parent")
    public R getCategoryParentListByPage(@Validated CategoryParentPageVo categoryParentPageVo) {
        Map<String, Object> param = BeanUtil.beanToMap(categoryParentPageVo);
        int page = (Integer) param.get("page");
        int length = (Integer) param.get("length");
        int start = (page - 1) * length;
        param.put("start",start);
        PageUtils pageutils = this.categoryService.searchCategoryParentListByPage(param);

        return R.ok()
                .put("result",pageutils);
    }

    /**
     * 根据id分页查询二级分类接口
     * @param categorySonListById 根据id分页获取二级分类列表VO对象
     * @return 分类分页列表
     */
    @ApiOperation(value = "根据id分页查询二级分类")
    @GetMapping("/categories/son")
    public R getCategoryListPageByParentId(@Validated CategorySonListById categorySonListById) {
        Map<String, Object> param = BeanUtil.beanToMap(categorySonListById);
        int page = (Integer) param.get("page");
        int length = (Integer) param.get("length");
        int start = (page - 1) * length;
        param.put("start",start);
        PageUtils pageUtils = this.categoryService.searchCategorySonListPageByPatentId(param);

        return R.ok()
                .put("result",pageUtils);
    }


    /**
     * 查询一级分类及其二级分类
     * @return 分类列表
     */
    @ApiOperation(value = "获取一级分类及其子分类")
    @GetMapping("/categories")
    public R getCategoryList() {
        List<CategoryListDTO> resultList = this.categoryService.searchCategoryList();
        return R.ok()
                .put("result",resultList);
    }

    /**
     * 添加分类接口
     * @param categoryAddVo 分类添加VO实体类
     * @return 添加分类所影响的行数
     */
    @ApiOperation(value = "添加分类")
    @GetMapping("/admin/categories")
    public R addCategory(@RequestParam @Validated CategoryAddVo categoryAddVo) {
        int role = this.categoryService.addCategory(categoryAddVo);
        return R.ok()
                .put("role", role);
    }


    // TODO：删除分类、修改分类


    /**
     * 根据分类名分页查询文章预览列表
     * @param articleCategoryVo 根据分类获取文章列表VO对象
     * @return 分类文章分页列表
     */
    @ApiOperation(value = "根据分类名分页查询文章预览列表")
    @GetMapping("/categories/articles")
    public R getArticlesPageByCategoryName(@Validated ArticleCategoryVo articleCategoryVo) {
        Map<String, Object> param = BeanUtil.beanToMap(articleCategoryVo);
        int page = (Integer) param.get("page");
        int length = (Integer) param.get("length");
        int start = (page - 1) * length;
        param.put("start",start);
        PageUtils pageUtils = this.categoryService.searchArticlePageByCategoryName(param);

        return R.ok()
                .put("result",pageUtils);
    }
}
