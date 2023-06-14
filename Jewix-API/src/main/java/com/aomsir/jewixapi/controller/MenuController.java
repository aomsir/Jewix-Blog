package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.annotation.OperateLog;
import com.aomsir.jewixapi.pojo.vo.MenuPageVo;
import com.aomsir.jewixapi.pojo.vo.RoleOfMenusAddVo;
import com.aomsir.jewixapi.service.MenuService;
import com.aomsir.jewixapi.util.PageUtils;
import com.aomsir.jewixapi.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * @Description: 菜单控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Api(tags = "菜单控制器")
@RestController
public class MenuController {
    @Resource
    private MenuService menuService;

    /**
     * 分页获取菜单分页列表
     * @param menuPageVo 分页获取菜单VO实体类
     * @return 分页数据
     */
    @PreAuthorize("hasAuthority('ADMIN_MENU_LIST')")
    @ApiOperation(value = "分页获取菜单分页列表")
    @GetMapping("/admin/menus")
    public R getMenuListByPage(MenuPageVo menuPageVo) {
        Map<String, Object> param = BeanUtil.beanToMap(menuPageVo);
        int page = (Integer) param.get("page");
        int length = (Integer) param.get("length");
        int start = (page - 1) * length;
        param.put("start",start);

        PageUtils pageUtils = this.menuService.searchMenusByPage(param);
        return R.ok()
                .put("result", pageUtils);
    }

    /**
     * 为角色分配菜单
     * @param roleOfMenusAddVo 角色菜单分配VO实体类
     * @return 分配所影响的行数
     */
    @PreAuthorize("hasAuthority('ADMIN_MENU_ROLE')")
    @OperateLog(optType = "为角色分配菜单")
    @ApiOperation(value = "为角色分配菜单")
    @PostMapping("/admin/menus/doAssign")
    public R doAssignMenusForRole(@RequestBody @Validated RoleOfMenusAddVo roleOfMenusAddVo) {
        int role = this.menuService.insertMenusForRole(roleOfMenusAddVo);
        return R.ok()
                .put("role", role);
    }
}
