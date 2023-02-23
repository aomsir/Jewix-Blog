package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.mapper.UserMapper;
import com.aomsir.jewixapi.pojo.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author: Aomsir
 * @Date: 2023/2/19
 * @Description: UserDetails业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userMapper.queryUserByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // TODO: 查询用户权限

        return user;
    }
}
