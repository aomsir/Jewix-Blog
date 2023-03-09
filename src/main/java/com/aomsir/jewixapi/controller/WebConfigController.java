package com.aomsir.jewixapi.controller;

import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.pojo.entity.WebConfig;
import com.aomsir.jewixapi.pojo.vo.InfoWebConfigAddVo;
import com.aomsir.jewixapi.pojo.vo.InfoWebConfigUpdateVo;
import com.aomsir.jewixapi.service.WebConfigService;
import com.aomsir.jewixapi.utils.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Author: Aomsir
 * @Date: 2023/3/7
 * @Description: 网站基本设置控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

// TODO: 完成评论等配置
@RestController
public class WebConfigController {

    @Resource
    private WebConfigService webConfigService;

    /**
     * 新增网站通用设置信息
     * @param infoWebConfigAddVo
     * @return
     */
    @PostMapping("/admin/configs")
    public R addInfoWebConfig(@RequestBody @Validated InfoWebConfigAddVo infoWebConfigAddVo) {

        int role = this.webConfigService.addWebConfig(infoWebConfigAddVo);
        return R.ok()
                .put("role",role);
    }


    /**
     * 修改网站通用设置信息
     * @param infoWebConfigUpdateVo
     * @return
     */
    @PutMapping("/admin/configs")
    public R updateInfoWebConfig(@RequestBody @Validated InfoWebConfigUpdateVo infoWebConfigUpdateVo) {

        int role = this.webConfigService.updateWebConfig(infoWebConfigUpdateVo);
        return R.ok()
                .put("role",role);
    }

    /**
     * 获取网站设置信息
     * @param type
     * @return
     */
    @GetMapping("/admin/configs")
    public R getInfoAll(@RequestParam Integer type) {
        if (type == null || type < 1 || type > 3) {
            throw new CustomerException("该类型不存在");
        }

        WebConfig webConfig = this.webConfigService.searchInfoAllByType(type);
        return R.ok()
                .put("result",webConfig);
    }



}
