package com.aomsir.jewixapi.controller;

import com.aomsir.jewixapi.annotation.OperateLog;
import com.aomsir.jewixapi.pojo.entity.WebConfig;
import com.aomsir.jewixapi.pojo.vo.InfoWebConfigAddVo;
import com.aomsir.jewixapi.pojo.vo.InfoWebConfigUpdateVo;
import com.aomsir.jewixapi.service.WebConfigService;
import com.aomsir.jewixapi.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: Aomsir
 * @Date: 2023/3/7
 * @Description: 网站设置控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Api(tags = "网站设置控制器")
@RestController
public class WebConfigController {

    @Resource
    private WebConfigService webConfigService;

    /**
     * 新增网站通用设置信息
     * @param infoWebConfigAddVo 网站通用设置信息VO对象
     * @return 新增信息所影响的行数
     */
    @OperateLog(optType = "新增网站信息")
    @PreAuthorize("hasAuthority('ADMIN_CONFIG_ADD')")
    @ApiOperation(value = "新增网站通用设置信息")
    @PostMapping("/admin/configs")
    public R addInfoWebConfig(@RequestBody @Validated InfoWebConfigAddVo infoWebConfigAddVo) {

        int role = this.webConfigService.addWebConfig(infoWebConfigAddVo);
        return R.ok()
                .put("role",role);
    }


    /**
     * 修改网站通用设置信息
     * @param infoWebConfigUpdateVo 更新网站通用设置信息VO对象
     * @return 更新信息所影响的行数
     */
    @OperateLog(optType = "修改网站信息")
    @PreAuthorize("hasAuthority('ADMIN_CONFIG_UPDATE')")
    @ApiOperation(value = "修改网站通用设置信息")
    @PutMapping("/admin/configs")
    public R updateInfoWebConfig(@RequestBody @Validated InfoWebConfigUpdateVo infoWebConfigUpdateVo) {

        int role = this.webConfigService.updateWebConfig(infoWebConfigUpdateVo);
        return R.ok()
                .put("role",role);
    }

    /**
     * 获取网站设置信息
     * @return 对应的网站信息
     */
    @ApiOperation(value = "根据类型获取网站设置信息")
    @GetMapping("/admin/configs")
    public R getInfoAll() {
        WebConfig webConfig = this.webConfigService.searchWebInfo();
        return R.ok()
                .put("result",webConfig);
    }
}
