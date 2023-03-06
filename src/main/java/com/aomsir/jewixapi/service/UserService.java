package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.dto.UserConfigDTO;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.pojo.vo.LoginVo;
import com.aomsir.jewixapi.utils.PageUtils;

import java.util.Map;

public interface UserService {

    User searchUserByUUID(String uuid);

    UserConfigDTO searchConfigUser();

    PageUtils searchUserByPage(Map<String, Object> param);
}
