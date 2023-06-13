package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.entity.Resource;
import com.aomsir.jewixapi.pojo.vo.RoleOfResourcesAddVo;
import com.aomsir.jewixapi.util.PageUtils;

import java.util.List;
import java.util.Map;

public interface ResourceService {
    List<Resource> searchResourcesByUserId(Long id);

    PageUtils searchResourcesByPage(Map<String, Object> param);

    int insertReaourceForRole(RoleOfResourcesAddVo addVo);
}
