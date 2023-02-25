package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.pojo.vo.CategoryAddVo;
import com.aomsir.jewixapi.pojo.vo.CategoryParentPageVo;
import com.aomsir.jewixapi.pojo.vo.CategorySonListById;
import com.aomsir.jewixapi.service.CategoryService;
import com.aomsir.jewixapi.utils.PageUtils;
import com.aomsir.jewixapi.utils.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/2/25
 * @Description: 分类控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@RestController
public class CategoryController {

    @Resource
    private CategoryService categoryService;


    /**
     * 分页查询一级分类列表
     * @param categoryParentPageVo
     * @return
     */
    @PostMapping("/category/parentList")
    public R getCategoryParentListByPage(@RequestBody @Validated CategoryParentPageVo categoryParentPageVo) {
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
     * @param categorySonListById
     * @return
     */
    @PostMapping("/category/sonList")
    public R getCategoryListPageByParentId(@RequestBody @Validated CategorySonListById categorySonListById) {
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
     * 通用分类添加接口
     * @param categoryAddVo
     * @return
     */
    @PostMapping("/admin/category/add")
    public R addCategory(@RequestBody @Validated CategoryAddVo categoryAddVo) {
        int role = this.categoryService.addCategory(categoryAddVo);
        return R.ok()
                .put("role", role);
    }
}
