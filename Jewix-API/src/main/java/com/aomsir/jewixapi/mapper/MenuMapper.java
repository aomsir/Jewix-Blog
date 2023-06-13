package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.dto.MenuListPageDTO;
import com.aomsir.jewixapi.pojo.entity.Menu;
import com.aomsir.jewixapi.pojo.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface MenuMapper {
    int queryMenuCountsOfParentByPage(@Param("param") Map<String, Object> param);

    ArrayList<Menu> queryMenuOfParentByPage(@Param("param") Map<String, Object> param);

    List<Menu> queryMenusByParentId(@Param("id") Integer id);


    int deleteMenusByRoleId(@Param("roleId") Integer roleId);

    Menu queryMenuById(@Param("menuId") Integer menuId);

    int insertResourcesForRole(@Param("roleId") Integer roleId, @Param("menuIds") List<Integer> menuIds);

    List<MenuListPageDTO> queryParentMenuByUserId(@Param("userId") Long id);

    List<Menu> queryMenusByParentIdAndUserId(@Param("parentId") Integer id, @Param("userId") Long id1);
}
