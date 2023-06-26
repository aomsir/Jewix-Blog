package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.entity.Resource;
import com.aomsir.jewixapi.pojo.vo.RoleOfResourcesAddVo;
import com.aomsir.jewixapi.util.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/6/26
 * @Description: 资源业务接口
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface ResourceService {

    /**
     * 根据用户id查询其资源列表
     * @param id 用户id
     * @return 资源列表
     */
    List<Resource> searchResourcesByUserId(Long id);


    /**
     * 分页查询资源列表
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchResourcesByPage(Map<String, Object> param);


    /**
     * 新增角色-资源关联数据
     * @param addVo 封装对象
     * @return 新增所影响的行数
     */
    int insertResourceForRole(RoleOfResourcesAddVo addVo);
}
