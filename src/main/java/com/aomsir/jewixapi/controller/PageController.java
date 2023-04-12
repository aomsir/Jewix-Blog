package com.aomsir.jewixapi.controller;

import com.aomsir.jewixapi.pojo.entity.Page;
import com.aomsir.jewixapi.pojo.vo.PageAddVo;
import com.aomsir.jewixapi.pojo.vo.PageDeleteVo;
import com.aomsir.jewixapi.pojo.vo.PageUpdateVo;
import com.aomsir.jewixapi.service.PageService;
import com.aomsir.jewixapi.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/3/21
 * @Description: 页面控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Api(tags = "页面控制器")
@RestController
public class PageController {

    @Resource
    private PageService pageService;


    /**
     * 获取页面列表
     * @return 分页页面列表
     */
    @ApiOperation(value = "获取页面列表")
    @GetMapping("/pages")
    public R getPageList() {
        List<Page> pageList = this.pageService.getPageList();
        return R.ok()
                .put("result", pageList);
    }

    /**
     * 获取页面
     * @param omit 路径名
     * @return 页面信息
     */
    @ApiOperation(value = "根据路径获取页面")
    @GetMapping("/pages/{omit}")
    public R getPageByOmit(@PathVariable("omit") Integer omit) {
        Page page = this.pageService.getPageByOmit(omit);
        return R.ok()
                .put("result", page);
    }


    /**
     * 添加页面
     * @param page 添加页面VO对象
     * @return 添加页面所影响的行数
     */
    @ApiOperation(value = "添加页面")
    @PostMapping("/admin/pages")
    public R addPage(@RequestBody @Validated PageAddVo page) {
        int role = this.pageService.addPage(page);
        return R.ok()
                .put("role", role);
    }

    /**
     * 修改页面
     * @param page 修改页面VO对象
     * @return 修改页面所影响的行数
     */
    @ApiOperation(value = "修改页面")
    @PutMapping("/admin/pages")
    public R updatePage(@RequestBody @Validated PageUpdateVo page) {
        int role = this.pageService.updatePage(page);
        return R.ok()
                .put("role", role);
    }

    /**
     * 删除页面
     * @param page 删除页面VO对象
     * @return 删除页面所影响的行数
     */
    @ApiOperation(value = "删除页面")
    @DeleteMapping("/admin/pages")
    public R deletePage(@RequestParam PageDeleteVo page) {
        int role = this.pageService.deletePage(page);
        return R.ok()
                .put("role", role);
    }


}
