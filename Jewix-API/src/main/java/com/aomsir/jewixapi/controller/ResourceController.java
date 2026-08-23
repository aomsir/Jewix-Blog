package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.annotation.OperateLog;
import com.aomsir.jewixapi.pojo.vo.ResourcePageVo;
import com.aomsir.jewixapi.pojo.vo.RoleOfResourcesAddVo;
import com.aomsir.jewixapi.service.ResourceService;
import com.aomsir.jewixapi.util.PageUtils;
import com.aomsir.jewixapi.util.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/6/8
 * @Description: 资源接口控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Tag(name = "资源控制器")
@RestController
public class ResourceController {

    @Resource
    private ResourceService resourceService;

    /**
     * 分页获取资源列表信息
     * @param resourcePageVo 资源分页VO对象
     * @return 通用返回数据
     */
    @PreAuthorize("hasAuthority('ADMIN_RESOURCE_LIST')")
    @Operation(summary = "分页获取资源列表信息")
    @GetMapping("/admin/resources")
    public R getResourceListByPage(ResourcePageVo resourcePageVo) {
        Map<String, Object> param = BeanUtil.beanToMap(resourcePageVo);
        int page = (Integer) param.get("page");
        int length = (Integer) param.get("length");
        int start = (page - 1) * length;
        param.put("start",start);

        PageUtils pageUtils = this.resourceService.searchResourcesByPage(param);
        return R.ok()
                .put("result", pageUtils);
    }

    /**
     * 为角色分配资源信息
     * @param addVo 角色分配资源VO实体类
     * @return 分配资源所影响的行数
     */
    @PreAuthorize("hasAuthority('ADMIN_RESOURCE_ROLE')")
    @OperateLog(optType = "为角色分配资源")
    @Operation(summary = "给角色分配资源权限")
    @PostMapping("/admin/resources/doAssign")
    public R doAssignResourceForRole(@RequestBody @Validated RoleOfResourcesAddVo addVo) {

        int role = this.resourceService.insertResourceForRole(addVo);
        return R.ok()
                .put("role", role);
    }
}
