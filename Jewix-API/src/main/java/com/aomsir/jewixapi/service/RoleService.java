package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.dto.RoleOfMenuDTO;
import com.aomsir.jewixapi.pojo.dto.RoleOfResourceDTO;
import com.aomsir.jewixapi.pojo.entity.Role;
import com.aomsir.jewixapi.pojo.vo.RoleAddVo;
import com.aomsir.jewixapi.pojo.vo.RoleUpdateVo;
import com.aomsir.jewixapi.util.PageUtils;

import java.util.List;
import java.util.Map;

public interface RoleService {
    PageUtils searchRoleListByPage(Map<String, Object> param);

    int addRole(RoleAddVo roleAddVo);

    int updateRoleInfo(RoleUpdateVo roleUpdateVo);

    int deleteRoles(List<Integer> roleIds);

    Role searchRoleById(Integer id);

    RoleOfMenuDTO searchRoleOfMenu(Integer id);

    RoleOfResourceDTO searchRoleOfResource(Integer id);
}
