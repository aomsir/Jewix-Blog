package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface ResourceMapper {
    List<Resource> queryAuthoritiesByUserId(@Param("userId") Long id);

    int queryResourceCountsOfParentByPage(@Param("param") Map<String, Object> param);

    ArrayList<Resource> queryResourcesOfParentByPage(@Param("param") Map<String, Object> param);

    List<Resource> queryResourcesByParentId(@Param("id") Integer id);

    Resource queryResourceById(@Param("resourceId") Integer resourceId);

    int deleteResourcesByRoleId(@Param("roleId") Integer roleId);

    int insertResourceForRole(@Param("roleId") Integer roleId, @Param("resourceIds") List<Integer> resourceIds);
}
