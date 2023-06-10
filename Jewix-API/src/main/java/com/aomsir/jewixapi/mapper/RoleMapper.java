package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface RoleMapper {
    int queryRoleCounts(@Param("param") Map<String, Object> param);

    ArrayList<Role> queryRoleListPage(@Param("param") Map<String, Object> param);

    Role queryRoleByRoleName(@Param("roleName") String roleName);

    Role queryRoleByRoleLabel(@Param("roleLabel") String roleLabel);

    int insertRole(@Param("param") Map<String, Object> param);

    Role queryRoleById(@Param("id") Integer id);

    int updateRoleInfo(@Param("param") Map<String, Object> param);

    int queryUserCountsByRoleId(@Param("roleId") Integer roleId);

    int deleteRoleForResource(@Param("roleId") Integer roleId);

    int deleteRoleForMenu(@Param("roleId") Integer roleId);

    int deleteRole(@Param("roleId") Integer roleId);
}
