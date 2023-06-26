package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.dto.MenuListPageDTO;
import com.aomsir.jewixapi.pojo.vo.RoleOfMenusAddVo;
import com.aomsir.jewixapi.util.PageUtils;

import java.util.List;
import java.util.Map;


/**
 * @Author: Aomsir
 * @Date: 2023/6/26
 * @Description: 菜单业务接口
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface MenuService {

    /**
     * 分页查询菜单数据
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchMenusByPage(Map<String, Object> param);


    /**
     * 新增角色-菜单关联数据
     * @param roleOfMenusAddVo 封装对象
     * @return 新增所影响的行数
     */
    int insertMenusForRole(RoleOfMenusAddVo roleOfMenusAddVo);


    /**
     * 根据用户id分页查询菜单列表
     * @param id 用户id
     * @return 菜单分页列表
     */
    List<MenuListPageDTO> searchMenusByUserId(Long id);
}
