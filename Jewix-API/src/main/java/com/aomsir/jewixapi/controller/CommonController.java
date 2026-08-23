package com.aomsir.jewixapi.controller;

import com.aomsir.jewixapi.pojo.dto.ArticleArchiveInfoDTO;
import com.aomsir.jewixapi.pojo.dto.WebInfoDTO;
import com.aomsir.jewixapi.service.CommonService;
import com.aomsir.jewixapi.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Aomsir
 * @Date: 2023/4/28
 * @Description: 通用控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Tag(name = "通用信息控制器")
@RestController
public class CommonController {

    @Resource
    private CommonService commonService;

    @Operation(summary = "获取站点全局信息")
    @GetMapping("/commons/webInfo")
    public R getCommonWebInfo() {
        WebInfoDTO webInfoDTO = this.commonService.searchWebInfo();
        return R.ok()
                .put("result",webInfoDTO);
    }

    /**
     *  归档全局信息
     * @return 归档信息
     */
    @Operation(summary = "获取归档全局信息")
    @GetMapping("/commons/articles/archiveInfo")
    public R getArticleArchiveInfo() {
        ArticleArchiveInfoDTO articleArchiveInfoDTO = this.commonService.searchArticleArchiveInfo();
        return R.ok()
                .put("result",articleArchiveInfoDTO);
    }
}
