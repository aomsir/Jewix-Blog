package com.aomsir.jewixapi.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.MenuMapper;
import com.aomsir.jewixapi.mapper.RoleMapper;
import com.aomsir.jewixapi.pojo.dto.CurrentUserDTO;
import com.aomsir.jewixapi.pojo.dto.MenuListPageDTO;
import com.aomsir.jewixapi.pojo.entity.Menu;
import com.aomsir.jewixapi.pojo.entity.Role;
import com.aomsir.jewixapi.pojo.vo.RoleOfMenusAddVo;
import com.aomsir.jewixapi.service.MenuService;
import com.aomsir.jewixapi.util.PageUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/6/8
 * @Description: 菜单业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public PageUtils searchMenusByPage(Map<String, Object> param) {
        // 查询父级
        int count = this.menuMapper.queryMenuCountsOfParentByPage(param);
        ArrayList<Menu> list = null;
        if (count > 0) {
            list = this.menuMapper.queryMenuOfParentByPage(param);
        }

        ArrayList<MenuListPageDTO> dtoList = new ArrayList<>();
        if (list != null) {
            for (Menu menu : list) {
                MenuListPageDTO pageDTO = new MenuListPageDTO();
                BeanUtil.copyProperties(menu, pageDTO);
                List<Menu> sonList = this.menuMapper.queryMenusByParentId(menu.getId());
                pageDTO.setSonList(sonList);
                dtoList.add(pageDTO);
            }
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        return new PageUtils(dtoList,count,start,length);
    }

    @Override
    public int insertMenusForRole(RoleOfMenusAddVo roleOfMenusAddVo) {
        // 查询角色
        Integer roleId = roleOfMenusAddVo.getRoleId();
        Role role = this.roleMapper.queryRoleById(roleId);
        if (role == null) {
            throw new CustomerException("角色不存在");
        }

        // 查询菜单
        List<Integer> menuIds = roleOfMenusAddVo.getMenuIds();
        for (Integer menuId : menuIds) {
            Menu menu = this.menuMapper.queryMenuById(menuId);
            if (menu == null) {
                throw new CustomerException("菜单不存在");
            }
        }

        // 删除当前角色的菜单
        this.menuMapper.deleteMenusByRoleId(roleId);

        // 新增当前角色菜单
        return this.menuMapper.insertResourcesForRole(roleId, menuIds);
    }

    @Override
    public List<MenuListPageDTO> searchMenusByUserId(Long id) {
        // 查询用户父级菜单列表
        List<MenuListPageDTO> menuListPageDTO = this.menuMapper.queryParentMenuByUserId(id);
        if (menuListPageDTO != null || !menuListPageDTO.isEmpty()) {

            // 查询每个父菜单下的子菜单
            for (MenuListPageDTO pageDTO : menuListPageDTO) {
                List<Menu> sonList = this.menuMapper.queryMenusByParentIdAndUserId(pageDTO.getId(), id);
                pageDTO.setSonList(sonList);
            }
        }

        return menuListPageDTO;
    }
}
