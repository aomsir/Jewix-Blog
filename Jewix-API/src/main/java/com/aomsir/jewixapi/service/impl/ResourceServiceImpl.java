package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.ResourceMapper;
import com.aomsir.jewixapi.mapper.UserMapper;
import com.aomsir.jewixapi.pojo.entity.Resource;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.service.ResourceService;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.aomsir.jewixapi.constant.UserConstants.USER_IS_NULL;

/**
 * @Author: Aomsir
 * @Date: 2023/6/8
 * @Description: 资源业务实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Service
public class ResourceServiceImpl implements ResourceService {

    @javax.annotation.Resource
    private UserMapper userMapper;

    @javax.annotation.Resource
    private ResourceMapper resourceMapper;

    @Override
    public List<Resource> searchResourcesByUserId(Long id) {
        User user = this.userMapper.queryUserById(id);
        if (user == null) {
            throw new CustomerException(USER_IS_NULL);
        }

        return this.resourceMapper.queryAuthoritiesByUserId(id);
    }
}
