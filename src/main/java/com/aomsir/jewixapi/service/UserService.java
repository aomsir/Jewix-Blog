package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.pojo.vo.LoginVo;

public interface UserService {

    public String login(LoginVo loginVo);
}
