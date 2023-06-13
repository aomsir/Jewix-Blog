package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.dto.CurrentUserDTO;
import com.aomsir.jewixapi.pojo.dto.MenuListPageDTO;
import com.aomsir.jewixapi.pojo.vo.RoleOfMenusAddVo;
import com.aomsir.jewixapi.util.PageUtils;

import java.util.List;
import java.util.Map;

public interface MenuService {
    PageUtils searchMenusByPage(Map<String, Object> param);

    int insertMenusForRole(RoleOfMenusAddVo roleOfMenusAddVo);

    List<MenuListPageDTO> searchMenusByUserId(Long id);
}
