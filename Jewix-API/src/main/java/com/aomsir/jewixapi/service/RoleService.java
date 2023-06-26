package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.dto.RoleOfMenuDTO;
import com.aomsir.jewixapi.pojo.dto.RoleOfResourceDTO;
import com.aomsir.jewixapi.pojo.entity.Role;
import com.aomsir.jewixapi.pojo.vo.RoleAddVo;
import com.aomsir.jewixapi.pojo.vo.RoleUpdateVo;
import com.aomsir.jewixapi.util.PageUtils;

import java.util.List;
import java.util.Map;


/**
 * @Author: Aomsir
 * @Date: 2023/6/26
 * @Description: 角色业务接口
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface RoleService {

    /**
     * 分页查询角色列表
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchRoleListByPage(Map<String, Object> param);


    /**
     * 新增角色
     * @param roleAddVo 封装对象
     * @return 新增所影响的行数
     */
    int addRole(RoleAddVo roleAddVo);


    /**
     * 更新角色信息
     * @param roleUpdateVo 封装对象
     * @return 更新所影响的行数
     */
    int updateRoleInfo(RoleUpdateVo roleUpdateVo);


    /**
     * 删除角色
     * @param roleIds 角色id列表
     * @return 删除所影响的行数
     */
    int deleteRoles(List<Integer> roleIds);


    /**
     * 根据id查询角色详情
     * @param id 角色id
     * @return 角色详情
     */
    Role searchRoleById(Integer id);


    /**
     * 查询角色关联的菜单信息
     * @param id 角色id
     * @return 角色-菜单封装对象
     */
    RoleOfMenuDTO searchRoleOfMenu(Integer id);


    /**
     * 查询角色关联的资源信息
     * @param id 角色id
     * @return 角色-资源封装对象
     */
    RoleOfResourceDTO searchRoleOfResource(Integer id);
}
