package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.annotation.OperateLog;
import com.aomsir.jewixapi.pojo.dto.CategoryListDTO;
import com.aomsir.jewixapi.pojo.vo.*;
import com.aomsir.jewixapi.service.CategoryService;
import com.aomsir.jewixapi.util.PageUtils;
import com.aomsir.jewixapi.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public R getCategoryParentListByPage(CategoryParentPageVo categoryParentPageVo) {
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
    public R getCategoryListPageByParentId(CategorySonListById categorySonListById) {
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
    @OperateLog(optType = "添加分类")
    @PreAuthorize("hasAuthority('ADMIN_CATEGORY_ADD')")
    @ApiOperation(value = "添加分类")
    @PostMapping("/admin/categories")
    public R addCategory(@RequestBody @Validated CategoryAddVo categoryAddVo) {
        int role = this.categoryService.addCategory(categoryAddVo);
        return R.ok()
                .put("role", role);
    }


    /**
     * 根据分类ID分页查询文章预览列表
     * @param articleCategoryVo 根据分类获取文章列表VO对象
     * @return 分类文章分页列表
     */
    @ApiOperation(value = "根据分类ID分页查询文章预览列表")
    @GetMapping("/categories/articles")
    public R getArticlesPageByCategoryId(ArticleCategoryVo articleCategoryVo) {
        Map<String, Object> param = BeanUtil.beanToMap(articleCategoryVo);
        int page = (Integer) param.get("page");
        int length = (Integer) param.get("length");
        int start = (page - 1) * length;
        param.put("start",start);
        PageUtils pageUtils = this.categoryService.searchArticlePageByCategoryName(param);

        return R.ok()
                .put("result",pageUtils);
    }


    /**
     * 删除分类信息接口
     * @param ids 待删除分类id列表
     * @return 影响的行数
     */
    @OperateLog(optType = "删除分类")
    @PreAuthorize("hasAuthority('ADMIN_CATEGORY_DELETE')")
    @ApiOperation(value = "删除分类信息")
    @DeleteMapping("/admin/categories")
    public R deleteCategory(@RequestParam List<Long> ids) {
        int role = this.categoryService.deleteCategories(ids);
        return R.ok()
                .put("role",role);
    }


    /**
     * 更新分类信息
     * @param categoryUpdateVo 更改分类VO对象
     * @return 所影响的行数
     */
    @OperateLog(optType = "修改分类")
    @PreAuthorize("hasAuthority('ADMIN_CATEGORY_UPDATE')")
    @ApiOperation(value = "更新分类信息")
    @PutMapping("/admin/categories")
    public R updateCategory(@RequestBody CategoryUpdateVo categoryUpdateVo) {
        int role = this.categoryService.updateCategory(categoryUpdateVo);
        return R.ok()
                .put("role",role);
    }
}
