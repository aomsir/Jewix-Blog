package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.mapper.ResourceMapper;
import com.aomsir.jewixapi.mapper.UserMapper;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.service.ResourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static com.aomsir.jewixapi.constant.UserConstants.USER_IS_NULL;

/**
 * @Author: Aomsir
 * @Date: 2023/2/19
 * @Description: UserDetails业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private UserMapper userMapper;

    @Resource
    private ResourceService resourceService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userMapper.queryUserByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException(USER_IS_NULL);
        }

        // 数据库查询权限
        List<com.aomsir.jewixapi.pojo.entity.Resource> resourceList = this.resourceService.searchResourcesByUserId(user.getId());
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        if (resourceList != null && resourceList.get(0) != null) {
            for (com.aomsir.jewixapi.pojo.entity.Resource resource : resourceList) {
                authorities.add(new SimpleGrantedAuthority(resource.getLabel().trim()));
            }
        }

        user.setAuthorities(authorities);
        return user;
    }
}
