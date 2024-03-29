package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.annotation.OperateLog;
import com.aomsir.jewixapi.pojo.dto.RoleOfMenuDTO;
import com.aomsir.jewixapi.pojo.dto.RoleOfResourceDTO;
import com.aomsir.jewixapi.pojo.entity.Role;
import com.aomsir.jewixapi.pojo.vo.RoleAddVo;
import com.aomsir.jewixapi.pojo.vo.RolePageVo;
import com.aomsir.jewixapi.pojo.vo.RoleUpdateVo;
import com.aomsir.jewixapi.service.RoleService;
import com.aomsir.jewixapi.util.PageUtils;
import com.aomsir.jewixapi.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/6/8
 * @Description: 角色控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Api(tags = "角色控制器")
@RestController
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 分页获取角色列表
     * @param rolePageVo 角色分页VO实体类
     * @return 通用返回数据
     */
    @PreAuthorize("hasAuthority('ADMIN_ROLE_LIST')")
    @ApiOperation(value = "分页获取角色列表")
    @GetMapping("/admin/roles")
    public R getRoleList(RolePageVo rolePageVo) {
        Map<String, Object> param = BeanUtil.beanToMap(rolePageVo);
        int page = (Integer) param.get("page");
        int length = (Integer) param.get("length");
        int start = (page - 1) * length;
        param.put("start",start);

        PageUtils pageUtils = this.roleService.searchRoleListByPage(param);
        return R.ok()
                .put("result",pageUtils);
    }


    /**
     *  添加角色
     * @param roleAddVo 角色添加VO实体类
     * @return 通用返回数据
     */
    @PreAuthorize("hasAuthority('ADMIN_ROLE_ADD')")
    @OperateLog(optType = "添加角色")
    @ApiOperation(value = "添加角色")
    @PostMapping("/admin/roles")
    public R addRole(@RequestBody @Validated RoleAddVo roleAddVo) {
        int role = this.roleService.addRole(roleAddVo);
        return R.ok()
                .put("role", role);
    }

    /**
     * 修改角色信息
     * @param roleUpdateVo 修改角色VO实体类
     * @return 通用返回数据
     */
    @PreAuthorize("hasAuthority('ADMIN_ROLE_UPDATE')")
    @OperateLog(optType = "修改角色信息")
    @ApiOperation(value = "修改角色信息")
    @PutMapping("/admin/roles")
    public R updateRole(@RequestBody @Validated RoleUpdateVo roleUpdateVo) {
        int role = this.roleService.updateRoleInfo(roleUpdateVo);
        return R.ok()
                .put("role", role);
    }

    /**
     * 删除角色
     * @param roleIds 角色ids
     * @return 通用返回数据
     */
    @PreAuthorize("hasAuthority('ADMIN_ROLE_DELETE')")
    @OperateLog(optType = "删除角色")
    @ApiOperation(value = "删除角色")
    @DeleteMapping("/admin/roles")
    public R deleteRoles(@RequestParam List<Integer> roleIds) {
        int role = this.roleService.deleteRoles(roleIds);
        return R.ok()
                .put("role", role);
    }


    /**
     * 根据id获取角色详细信息
     * @param id 角色id
     * @return 通用角色id
     */
    @PreAuthorize("hasAuthority('ADMIN_ROLE_DETAIL')")
    @ApiOperation(value = "根据id获取角色详细信息")
    @GetMapping("/admin/roles/{id}")
    public R getRoleInfoById(@PathVariable("id") Integer id) {
        Role role = this.roleService.searchRoleById(id);
        return R.ok()
                .put("result", role);
    }

    /**
     * 根据角色id获取菜单列表
     * @param id 角色id
     * @return 角色对应的菜单信息
     */
    @PreAuthorize("hasAuthority('ADMIN_ROLE_MENU_LIST')")
    @ApiOperation(value = "根据角色id获取菜单列表")
    @GetMapping("/admin/roles/menu")
    public R getMenusInfoByRoleId(Integer id) {
        RoleOfMenuDTO roleOfMenuDTO = this.roleService.searchRoleOfMenu(id);
        return R.ok()
                .put("result", roleOfMenuDTO);
    }

    /**
     * 根据角色id获取资源列表
     * @param id 角色id
     * @return 角色对应的资源信息
     */
    @PreAuthorize("hasAuthority('ADMIN_ROLE_RESOURCE_LIST')")
    @ApiOperation(value = "根据角色id获取资源列表")
    @GetMapping("/admin/roles/resource")
    public R getResourcesInfoByRoleId(Integer id) {
        RoleOfResourceDTO roleOfResourceDTO = this.roleService.searchRoleOfResource(id);
        return R.ok()
                .put("result", roleOfResourceDTO);
    }

}
