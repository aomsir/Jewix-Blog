package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/4/15
 * @Description: 接口资源Mapper
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Mapper
public interface ResourceMapper {
    /**
     * 根据用户id查询接口资源列表
     * @param id 用户id
     * @return 接口资源列表
     */
    List<Resource> queryAuthoritiesByUserId(@Param("userId") Long id);

    /**
     * 分页查询分资源数量
     * @param param 参数列表
     * @return 接口资源数量
     */
    int queryResourceCountsOfParentByPage(@Param("param") Map<String, Object> param);

    /**
     * 分页查询分级资源列表
     * @param param 参数列表
     * @return 接口资源列表
     */
    ArrayList<Resource> queryResourcesOfParentByPage(@Param("param") Map<String, Object> param);

    /**
     * 根据父级id查询接口资源数量
     * @param id 父级资源id
     * @return 接口资源数量
     */
    List<Resource> queryResourcesByParentId(@Param("id") Integer id);

    /**
     * 根据id查询接口资源
     * @param resourceId 资源id
     * @return 接口资源详情
     */
    Resource queryResourceById(@Param("resourceId") Integer resourceId);

    /**
     * 根据角色id删除对应关联的资源id
     * @param roleId 角色id
     * @return 删除所影响的行数
     */
    int deleteResourcesByRoleId(@Param("roleId") Integer roleId);

    /**
     * 新增角色-资源关联数据
     * @param roleId 角色id
     * @param resourceIds 资源id列表
     * @return 新增所影响的行数
     */
    int insertResourceForRole(@Param("roleId") Integer roleId, @Param("resourceIds") List<Integer> resourceIds);

    /**
     * 根据角色id查询对应的接口资源列表
     * @param id 角色id
     * @param parentId 父级资源id
     * @return 资源列表
     */
    List<Resource> queryResourceByRoleId(@Param("id") Integer id,
                                         @Param("parentId") Integer parentId);
}
