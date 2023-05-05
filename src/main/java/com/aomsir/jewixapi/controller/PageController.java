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



}
