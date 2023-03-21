package com.aomsir.jewixapi.controller;

import com.aomsir.jewixapi.pojo.entity.Page;
import com.aomsir.jewixapi.pojo.vo.PageAddVo;
import com.aomsir.jewixapi.pojo.vo.PageDeleteVo;
import com.aomsir.jewixapi.pojo.vo.PageUpdateVo;
import com.aomsir.jewixapi.service.PageService;
import com.aomsir.jewixapi.utils.R;
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

@RestController
public class PageController {

    @Resource
    private PageService pageService;


    /**
     * 获取页面列表
     * @return
     */
    @GetMapping("/page")
    public R getPageList() {
        List<Page> pageList = this.pageService.getPageList();
        return R.ok()
                .put("result", pageList);
    }

    /**
     * 获取页面
     * @param omit
     * @return
     */
    @GetMapping("/page/{omit}")
    public R getPageByOmit(@PathVariable("omit") Integer omit) {
        Page page = this.pageService.getPageByOmit(omit);
        return R.ok()
                .put("result", page);
    }


    /**
     * 添加页面
     * @param page
     * @return
     */
    @PostMapping("/admin/page")
    public R addPage(@RequestBody @Validated PageAddVo page) {
        int role = this.pageService.addPage(page);
        return R.ok()
                .put("role", role);
    }

    /**
     * 修改页面
     * @param page
     * @return
     */
    @PutMapping("/admin/page")
    public R updatePage(@RequestBody @Validated PageUpdateVo page) {
        int role = this.pageService.updatePage(page);
        return R.ok()
                .put("role", role);
    }

    /**
     * 删除页面
     * @param page
     * @return
     */
    @DeleteMapping("/admin/page")
    public R deletePage(@RequestBody @Validated PageDeleteVo page) {
        int role = this.pageService.deletePage(page);
        return R.ok()
                .put("role", role);
    }
}
