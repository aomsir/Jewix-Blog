package com.aomsir.jewixapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.RoleMapper;
import com.aomsir.jewixapi.pojo.entity.Role;
import com.aomsir.jewixapi.pojo.vo.RoleAddVo;
import com.aomsir.jewixapi.pojo.vo.RoleUpdateVo;
import com.aomsir.jewixapi.service.RoleService;
import com.aomsir.jewixapi.util.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/6/8
 * @Description: 角色业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;


    @Override
    public PageUtils searchRoleListByPage(Map<String, Object> param) {
        int count = this.roleMapper.queryRoleCounts(param);
        ArrayList<Role> list = null;
        if (count > 0) {
            list = this.roleMapper.queryRoleListPage(param);
        } else {
            list = new ArrayList<>();
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        return new PageUtils(list, count, start, length);
    }

    @Override
    @Transactional
    public int addRole(RoleAddVo roleAddVo) {
        Role role_1 = this.roleMapper.queryRoleByRoleName(roleAddVo.getRoleName());
        if (role_1 != null) {
            throw new CustomerException("角色名已存在");
        }

        Role role_2 = this.roleMapper.queryRoleByRoleLabel(roleAddVo.getRoleLabel());
        if (role_2 != null) {
            throw new CustomerException("角色标签已存在");
        }

        Map<String, Object> param = BeanUtil.beanToMap(roleAddVo);
        param.put("createTime", new Date());
        param.put("updateTime", new Date());

        return this.roleMapper.insertRole(param);
    }

    @Override
    @Transactional
    public int updateRoleInfo(RoleUpdateVo roleUpdateVo) {
        Role role_1 = this.roleMapper.queryRoleById(roleUpdateVo.getId());
        if (role_1 == null) {
            throw new CustomerException("角色不存在");
        }

        Role role_2 = this.roleMapper.queryRoleByRoleName(roleUpdateVo.getRoleName());
        if (role_2 != null && !role_2.getId().equals(roleUpdateVo.getId())) {
            throw new CustomerException("角色名已存在");
        }

        Role role_3 = this.roleMapper.queryRoleByRoleLabel(roleUpdateVo.getRoleLabel());
        if (role_3 != null && !role_3.getId().equals(roleUpdateVo.getId())) {
            throw new CustomerException("角色标签已存在");
        }

        Map<String, Object> param = BeanUtil.beanToMap(roleUpdateVo);
        param.put("updateTime", new Date());

        return this.roleMapper.updateRoleInfo(param);
    }

    @Override
    public int deleteRoles(List<Integer> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            throw new CustomerException("待删除列表为空");
        }

        for (Integer roleId : roleIds) {
            Role role = this.roleMapper.queryRoleById(roleId);
            if (role == null) {
                throw new CustomerException("待删除角色不存在");
            }

            int counts = this.roleMapper.queryUserCountsByRoleId(roleId);
            if (counts > 0) {
                throw new CustomerException("所选角色下有用户");
            }
        }


        int role = 0;
        for (Integer roleId : roleIds) {
            this.roleMapper.deleteRoleForResource(roleId);
            this.roleMapper.deleteRoleForMenu(roleId);
            role = this.roleMapper.deleteRole(roleId);
        }
        return role;
    }

    @Override
    public Role searchRoleById(Integer id) {
        if (id == null) {
            throw new CustomerException("参数异常");
        }

        Role role = this.roleMapper.queryRoleById(id);
        if (role == null) {
            throw new CustomerException("角色不存在");
        }
        return role;
    }
}
