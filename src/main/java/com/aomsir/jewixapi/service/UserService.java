package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.dto.UserConfigDTO;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.pojo.vo.*;
import com.aomsir.jewixapi.utils.PageUtils;

import java.util.List;
import java.util.Map;

public interface UserService {

    User searchUserByUUID(String uuid);

    UserConfigDTO searchConfigUser();

    PageUtils searchUserByPage(Map<String, Object> param);

    int addUser(UserAddVo userAddVo);

    int updateUser(UserUpdateVo userUpdateVo);

    int hasUser(UserHaveVo userHaveVo);

    int updateStatus(UserStatusVo userStatusVo);

    User searchCurrentUser();

    int deleteUserByArchive(List<Long> ids);

    int deleteUserByPhysics(List<Long> ids);
}
