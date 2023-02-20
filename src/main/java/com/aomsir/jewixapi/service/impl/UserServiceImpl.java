package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.mapper.UserMapper;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.pojo.vo.LoginVo;
import com.aomsir.jewixapi.service.UserService;
import com.aomsir.jewixapi.utils.BlogPasswordEncoder;
import com.aomsir.jewixapi.utils.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/2/19
 * @Description: 用户服务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private UserMapper userMapper;

    @Resource
    BlogPasswordEncoder blogPasswordEncoder;


    @Override
    public String login(LoginVo loginVo) {
        User user = this.userMapper.queryUserByEmail(loginVo.getUsername());

        if (user == null) {
            throw new RuntimeException("用户不存在");
        }

        if (!this.blogPasswordEncoder.matches(loginVo.getPassword() + user.getSalt(), user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        Map temp = new HashMap(){{
            put("userId", user.getId());
        }};
        String token = JwtUtils.getToken(temp);
        return token;
    }
}
