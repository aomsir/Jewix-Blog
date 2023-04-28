package com.aomsir.jewixapi.controller;

import com.aomsir.jewixapi.pojo.dto.WebInfoDTO;
import com.aomsir.jewixapi.service.CommonService;
import com.aomsir.jewixapi.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "通用信息控制器")
@RestController
public class CommonController {

    @Resource
    private CommonService commonService;

    @ApiOperation("获取站点全局信息")
    @GetMapping("/commons/webInfo")
    public R getCommonWebInfo() {
        WebInfoDTO webInfoDTO = this.commonService.queryWebInfo();
        return R.ok()
                .put("result",webInfoDTO);
    }
}
