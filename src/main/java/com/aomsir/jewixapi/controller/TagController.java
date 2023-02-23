package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.pojo.vo.TagPageVo;
import com.aomsir.jewixapi.service.TagService;
import com.aomsir.jewixapi.utils.PageUtils;
import com.aomsir.jewixapi.utils.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;


/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description: 标签控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@RestController
public class TagController {

    @Resource
    private TagService tagService;

    @GetMapping("/tag/list")
    public R getTagListByPage(@RequestBody @Validated TagPageVo tagPageVo) {
        Map<String, Object> param = BeanUtil.beanToMap(tagPageVo);
        int page = tagPageVo.getPage();
        int length = tagPageVo.getLength();
        int start = (page - 1) * length;
        param.put("start", start);
        PageUtils pageUtils = this.tagService.searchTagByPage(param);
        return R.ok()
                .put("result", pageUtils);
    }
}
