package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/4/15
 * @Description: 角色Mapper
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Mapper
public interface RoleMapper {
    /**
     * 查询角色数量
     * @param param 参数列表
     * @return 角色数量
     */
    int queryRoleCounts(@Param("param") Map<String, Object> param);

    /**
     * 分页查询角色列表
     * @param param 参数列表
     * @return 角色列表数据
     */
    ArrayList<Role> queryRoleListPage(@Param("param") Map<String, Object> param);

    /**
     * 根据名称查询角色详情
     * @param roleName 角色名称
     * @return 角色详情
     */
    Role queryRoleByRoleName(@Param("roleName") String roleName);

    /**
     * 根据标签查询角色详情
     * @param roleLabel 角色标签
     * @return 角色详情
     */
    Role queryRoleByRoleLabel(@Param("roleLabel") String roleLabel);

    /**
     * 新增角色
     * @param param 参数列表
     * @return 新增角色所影响的行数
     */
    int insertRole(@Param("param") Map<String, Object> param);

    /**
     * 根据id查询角色详情
     * @param id 角色id
     * @return 角色详情
     */
    Role queryRoleById(@Param("id") Integer id);

    /**
     * 更新角色信息
     * @param param 参数列表
     * @return 更新所影响的行数
     */
    int updateRoleInfo(@Param("param") Map<String, Object> param);

    /**
     * 根据角色id查询所关联的用户数量
     * @param roleId 角色id
     * @return 用户数量
     */
    int queryUserCountsByRoleId(@Param("roleId") Integer roleId);

    /**
     * 删除角色关联的对应资源信息
     * @param roleId 角色id
     * @return 删除所影响的行数
     */
    int deleteRoleForResource(@Param("roleId") Integer roleId);

    /**
     * 删除角色关联的对应菜单信息
     * @param roleId 角色id
     * @return 删除所影响的行数
     */
    int deleteRoleForMenu(@Param("roleId") Integer roleId);

    /**
     * 删除角色
     * @param roleId 角色id
     * @return 删除角色所影响的行数
     */
    int deleteRole(@Param("roleId") Integer roleId);
}
