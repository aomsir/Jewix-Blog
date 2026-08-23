package com.aomsir.jewixapi.controller;

import com.aomsir.jewixapi.annotation.OperateLog;
import com.aomsir.jewixapi.pojo.dto.PageListDTO;
import com.aomsir.jewixapi.pojo.entity.Page;
import com.aomsir.jewixapi.pojo.vo.PageAddVo;
import com.aomsir.jewixapi.pojo.vo.PageUpdateVo;
import com.aomsir.jewixapi.service.PageService;
import com.aomsir.jewixapi.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
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

@Tag(name = "页面控制器")
@RestController
public class PageController {

    @Resource
    private PageService pageService;


    /**
     * 查询页面列表数据(前后台使用一个接口)
     * @return 页面列表数据
     */
    @Operation(summary = "查询页面列表数据")
    @GetMapping("/pages")
    public R getPageList() {
        List<PageListDTO> result = this.pageService.searchPageList();
        return R.ok()
                .put("result",result);
    }

    /**
     * 根据uuid查询页面详细信息
     * @param uuid 页面uuid
     * @return 页面详细信息
     */
    @Operation(summary = "获取页面详细内容")
    @GetMapping("/pages/{uuid}")
    public R getPageDetail(@PathVariable("uuid") String uuid) {
        Page page = this.pageService.searchPageByUuid(uuid);
        return R.ok()
                .put("result",page);
    }


    /**
     * 新增页面
     * @param pageAddVo 页面添加实体类
     * @return 新增影响的行数
     */
    @OperateLog(optType = "新增页面")
    @PreAuthorize("hasAuthority('ADMIN_PAGE_ADD')")
    @Operation(summary = "新增页面")
    @PostMapping("/admin/pages")
    public R addPage(@RequestBody @Validated PageAddVo pageAddVo) {
        int role = this.pageService.addPage(pageAddVo);
        return R.ok()
                .put("role",role);
    }


    /**
     * 更新页面
     * @param pageUpdateVo 页面更新实体类
     * @return 更新影响的行数
     */
    @OperateLog(optType = "修改页面")
    @PreAuthorize("hasAuthority('ADMIN_PAGE_UPDATE')")
    @Operation(summary = "更新页面")
    @PutMapping("/admin/pages")
    public R updatePage(@RequestBody @Validated PageUpdateVo pageUpdateVo) {
        int role = this.pageService.updatePage(pageUpdateVo);
        return R.ok()
                .put("role",role);
    }


    /**
     * 删除页面
     * @param uuid 页面uuid
     * @return 删除影响的行数
     */
    @OperateLog(optType = "删除页面")
    @PreAuthorize("hasAuthority('ADMIN_PAGE_DELETE')")
    @Operation(summary = "删除页面")
    @DeleteMapping("/admin/pages")
    public R deletePage(String uuid) {
        int role = this.pageService.deletePage(uuid);
        return R.ok()
                .put("role",role);
    }
}
