package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.UserMapper;
import com.aomsir.jewixapi.pojo.dto.UserConfigDTO;
import com.aomsir.jewixapi.pojo.entity.Tag;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.pojo.vo.LoginVo;
import com.aomsir.jewixapi.service.UserService;
import com.aomsir.jewixapi.utils.JwtUtils;
import com.aomsir.jewixapi.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/2/19
 * @Description: 用户业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UserMapper userMapper;


    @Override
    public User searchUserByUUID(String uuid) {
        if (uuid == null) {
            throw new CustomerException("uuid为空!");
        }

        User user = this.userMapper.queryUserByUUID(uuid);

        return user;
    }

    @Override
    public UserConfigDTO searchConfigUser() {
        UserConfigDTO userConfigDTO = this.userMapper.queryConfigUser();
        if (userConfigDTO == null) {
            throw new CustomerException("用户不存在");
        }
        return userConfigDTO;
    }

    @Override
    public PageUtils searchUserByPage(Map<String, Object> param) {
        Long count = this.userMapper.queryUserCount((Integer)param.get("status"),(String)param.get("email"));
        ArrayList<User> list = null;
        if (count > 0) {
            list = this.userMapper.queryUserListByPage(param);
        } else {
            list = new ArrayList<>();
        }
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list,count,start,length);
        return pageUtils;
    }
}
