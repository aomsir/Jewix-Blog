package com.aomsir.jewixapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.ResourceMapper;
import com.aomsir.jewixapi.mapper.RoleMapper;
import com.aomsir.jewixapi.mapper.UserMapper;
import com.aomsir.jewixapi.pojo.dto.ResourceListPageDTO;
import com.aomsir.jewixapi.pojo.entity.Resource;
import com.aomsir.jewixapi.pojo.entity.Role;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.pojo.vo.RoleOfResourcesAddVo;
import com.aomsir.jewixapi.service.ResourceService;
import com.aomsir.jewixapi.util.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.aomsir.jewixapi.constant.UserConstants.USER_IS_NULL;

/**
 * @Author: Aomsir
 * @Date: 2023/6/8
 * @Description: 资源业务实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @javax.annotation.Resource
    private UserMapper userMapper;

    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    @javax.annotation.Resource
    private RoleMapper roleMapper;

    @Override
    public List<Resource> searchResourcesByUserId(Long id) {
        User user = this.userMapper.queryUserById(id);
        if (user == null) {
            throw new CustomerException(USER_IS_NULL);
        }

        return this.resourceMapper.queryAuthoritiesByUserId(id);
    }

    @Override
    public PageUtils searchResourcesByPage(Map<String, Object> param) {
        // 查询父级
        int count = this.resourceMapper.queryResourceCountsOfParentByPage(param);
        ArrayList<Resource> list = null;
        if (count > 0) {
            list = this.resourceMapper.queryResourcesOfParentByPage(param);
        }

        ArrayList<ResourceListPageDTO> dtoList = new ArrayList<>();
        if (list != null) {
            for (Resource resource : list) {
                ResourceListPageDTO pageDTO = new ResourceListPageDTO();
                BeanUtil.copyProperties(resource, pageDTO);
                List<Resource> sonList = this.resourceMapper.queryResourcesByParentId(resource.getId());
                pageDTO.setResourceSons(sonList);
                dtoList.add(pageDTO);
            }
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        return new PageUtils(dtoList,count,start,length);
    }

    @Override
    @Transactional
    public int insertReaourceForRole(RoleOfResourcesAddVo addVo) {
        // 查询角色
        Integer roleId = addVo.getRoleId();
        Role role = this.roleMapper.queryRoleById(roleId);
        if (role == null) {
            throw new CustomerException("角色不存在");
        }

        // 查询资源
        List<Integer> resourceIds = addVo.getResourceIds();
        for (Integer resourceId : resourceIds) {
            Resource resource = this.resourceMapper.queryResourceById(resourceId);
            if (resource == null) {
                throw new CustomerException("资源不存在");
            }
        }

        // 删除当前角色的资源
        this.resourceMapper.deleteResourcesByRoleId(roleId);

        // 新增当前角色资源
        return this.resourceMapper.insertResourceForRole(roleId, resourceIds);
    }
}
