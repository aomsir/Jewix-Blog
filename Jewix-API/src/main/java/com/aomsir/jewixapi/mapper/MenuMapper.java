package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.dto.MenuListPageDTO;
import com.aomsir.jewixapi.pojo.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/4/15
 * @Description: 菜单Mapper
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Mapper
public interface MenuMapper {
    /**
     * 分页查询父级菜单数量
     * @param param 参数列表
     * @return 菜单数量
     */
    int queryMenuCountsOfParentByPage(@Param("param") Map<String, Object> param);

    /**
     * 分页查询父级菜单
     * @param param 参数列表
     * @return 分级菜单列表
     */
    ArrayList<Menu> queryMenuOfParentByPage(@Param("param") Map<String, Object> param);

    /**
     * 根据父类id查询菜单列表
     * @param id 父菜单id
     * @return 菜单列表
     */
    List<Menu> queryMenusByParentId(@Param("id") Integer id);


    /**
     * 根据角色id删除对应引用菜单
     * @param roleId 角色id
     * @return  删除所影响的行数
     */
    int deleteMenusByRoleId(@Param("roleId") Integer roleId);

    /**
     * 根据id查询菜单详情
     * @param menuId 菜单id
     * @return 菜单详情
     */
    Menu queryMenuById(@Param("menuId") Integer menuId);

    /**
     * 插入角色id与菜单id的关联信息
     * @param roleId 角色id
     * @param menuIds 菜单id列表
     * @return 插入所影响的行数
     */
    int insertResourcesForRole(@Param("roleId") Integer roleId, @Param("menuIds") List<Integer> menuIds);

    /**
     * 根据用户id查询父级菜单列表
     * @param id 用户id
     * @return 菜单列表
     */
    List<MenuListPageDTO> queryParentMenuByUserId(@Param("userId") Long id);

    /**
     * 根据父级菜单与用户id查询菜单列表
     * @param id 父菜单id
     * @param id1 用户id
     * @return 菜单列表
     */
    List<Menu> queryMenusByParentIdAndUserId(@Param("parentId") Integer id, @Param("userId") Long id1);

    /**
     * 根据角色id查询对应的菜单列表
     * @param id 角色id
     * @param parentId 父级分类id
     * @return 菜单列表
     */
    List<Menu> queryMenuByRoleId(@Param("roleId") Integer id,
                                 @Param("parentId") Integer parentId);
}
