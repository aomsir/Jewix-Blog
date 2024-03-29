package com.aomsir.jewixapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.MenuMapper;
import com.aomsir.jewixapi.mapper.ResourceMapper;
import com.aomsir.jewixapi.mapper.RoleMapper;
import com.aomsir.jewixapi.pojo.dto.MenuListPageDTO;
import com.aomsir.jewixapi.pojo.dto.ResourceListPageDTO;
import com.aomsir.jewixapi.pojo.dto.RoleOfMenuDTO;
import com.aomsir.jewixapi.pojo.dto.RoleOfResourceDTO;
import com.aomsir.jewixapi.pojo.entity.Menu;
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

import static com.aomsir.jewixapi.constant.CommonConstants.PARAMETER_ERROR;
import static com.aomsir.jewixapi.constant.RoleConstants.*;

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

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private ResourceMapper resourceMapper;


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
    @Transactional(rollbackFor = Exception.class)
    public int addRole(RoleAddVo roleAddVo) {
        Role role1 = this.roleMapper.queryRoleByRoleName(roleAddVo.getRoleName());
        if (role1 != null) {
            throw new CustomerException(ROLE_NAME_HAS_EXISTED);
        }

        Role role2 = this.roleMapper.queryRoleByRoleLabel(roleAddVo.getRoleLabel());
        if (role2 != null) {
            throw new CustomerException(ROLE_LABEL_HAS_EXISTED);
        }

        Map<String, Object> param = BeanUtil.beanToMap(roleAddVo);
        param.put("createTime", new Date());
        param.put("updateTime", new Date());

        return this.roleMapper.insertRole(param);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateRoleInfo(RoleUpdateVo roleUpdateVo) {
        Role role1 = this.roleMapper.queryRoleById(roleUpdateVo.getId());
        if (role1 == null) {
            throw new CustomerException(ROLE_NOT_FOUND);
        }

        Role role2 = this.roleMapper.queryRoleByRoleName(roleUpdateVo.getRoleName());
        if (role2 != null && !role2.getId().equals(roleUpdateVo.getId())) {
            throw new CustomerException(ROLE_NAME_HAS_EXISTED);
        }

        Role role3 = this.roleMapper.queryRoleByRoleLabel(roleUpdateVo.getRoleLabel());
        if (role3 != null && !role3.getId().equals(roleUpdateVo.getId())) {
            throw new CustomerException(ROLE_LABEL_HAS_EXISTED);
        }

        Map<String, Object> param = BeanUtil.beanToMap(roleUpdateVo);
        param.put("updateTime", new Date());

        return this.roleMapper.updateRoleInfo(param);
    }

    @Override
    public int deleteRoles(List<Integer> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            throw new CustomerException(ROLE_LIST_IS_NULL);
        }

        for (Integer roleId : roleIds) {
            Role role = this.roleMapper.queryRoleById(roleId);
            if (role == null) {
                throw new CustomerException(ROLE_DELETE_IS_NULL);
            }

            int counts = this.roleMapper.queryUserCountsByRoleId(roleId);
            if (counts > 0) {
                throw new CustomerException(ROLE_HAS_USER);
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
            throw new CustomerException(PARAMETER_ERROR);
        }

        Role role = this.roleMapper.queryRoleById(id);
        if (role == null) {
            throw new CustomerException(ROLE_NOT_FOUND);
        }
        return role;
    }

    @Override
    public RoleOfMenuDTO searchRoleOfMenu(Integer id) {
        Role role = this.roleMapper.queryRoleById(id);
        if (role == null) {
            throw new CustomerException(ROLE_NOT_FOUND);
        }

        // 复制角色数据
        RoleOfMenuDTO roleOfMenuDTO = new RoleOfMenuDTO();
        BeanUtil.copyProperties(role, roleOfMenuDTO);

        // 根据角色查询父级菜单数据封装
        List<Menu> menus = this.menuMapper.queryMenuByRoleId(id,0);
        List<MenuListPageDTO> menuListPageDTOS = new ArrayList<>();
        for (Menu menu : menus) {
            MenuListPageDTO menuListPageDTO = new MenuListPageDTO();
            BeanUtil.copyProperties(menu, menuListPageDTO);
            menuListPageDTOS.add(menuListPageDTO);
        }

        // 根据角色查询二级菜单并数据封装
        for (Menu menu : menus) {
            List<Menu> sonList = this.menuMapper.queryMenuByRoleId(id, menu.getId());
            for (MenuListPageDTO menuListPageDTO : menuListPageDTOS) {
                if (menuListPageDTO.getId().equals(menu.getId())) {
                    menuListPageDTO.setSonList(sonList);
                }
            }
        }

        roleOfMenuDTO.setMenuListPageDTOs(menuListPageDTOS);
        return roleOfMenuDTO;
    }

    @Override
    public RoleOfResourceDTO searchRoleOfResource(Integer id) {
        Role role = this.roleMapper.queryRoleById(id);
        if (role == null) {
            throw new CustomerException(ROLE_NOT_FOUND);
        }

        // 复制角色数据
        RoleOfResourceDTO roleOfResourceDTO = new RoleOfResourceDTO();
        BeanUtil.copyProperties(role, roleOfResourceDTO);

        // 根据角色查询父级资源并数据封装
        List<com.aomsir.jewixapi.pojo.entity.Resource> resources = this.resourceMapper.queryResourceByRoleId(id, 0);
        List<ResourceListPageDTO> resourceListPageDTOS = new ArrayList<>();
        for (com.aomsir.jewixapi.pojo.entity.Resource resource : resources) {
            ResourceListPageDTO resourceListPageDTO = new ResourceListPageDTO();
            BeanUtil.copyProperties(resource, resourceListPageDTO);
            resourceListPageDTOS.add(resourceListPageDTO);
        }

        // 根据角色查询二级角色并数据封装
        for (com.aomsir.jewixapi.pojo.entity.Resource resource : resources) {
            List< com.aomsir.jewixapi.pojo.entity.Resource> sonList = this.resourceMapper.queryResourceByRoleId(id, resource.getId());
            for (ResourceListPageDTO resourceListPageDTO : resourceListPageDTOS) {
                if (resourceListPageDTO.getId().equals(resource.getId())) {
                    resourceListPageDTO.setResourceSons(sonList);
                }
            }
        }

        roleOfResourceDTO.setResourceListPageDTOList(resourceListPageDTOS);
        return roleOfResourceDTO;
    }
}
