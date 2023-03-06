package com.aomsir.jewixapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.UserMapper;
import com.aomsir.jewixapi.pojo.dto.UserConfigDTO;
import com.aomsir.jewixapi.pojo.entity.Tag;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.pojo.vo.LoginVo;
import com.aomsir.jewixapi.pojo.vo.UserAddVo;
import com.aomsir.jewixapi.pojo.vo.UserUpdateVo;
import com.aomsir.jewixapi.service.UserService;
import com.aomsir.jewixapi.utils.JwtUtils;
import com.aomsir.jewixapi.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
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

    @Resource
    private PasswordEncoder passwordEncoder;


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


    @Override
    @Transactional
    public int addUser(UserAddVo userAddVo) {
        User user_1 = this.userMapper.queryUserByEmail(userAddVo.getEmail());
        User user_2 = this.userMapper.queryUserByNickname(userAddVo.getNickname());

        if (user_1 != null) {
            throw new CustomerException("邮箱已存在,请重新注册");
        }

        if (user_2 != null) {
            throw new CustomerException("用户名已存在,请重新注册");
        }


        // 账号加密
        String password= this.passwordEncoder.encode(user_1.getPassword());

        Map<String, Object> param = BeanUtil.beanToMap(userAddVo);
        param.put("createTime",new Date());
        param.put("updateTime",new Date());
        param.put("password", password);
        param.put("uuid", UUID.randomUUID());
        param.put("salt",UUID.randomUUID());
        param.put("status",1);
        int role = this.userMapper.insertUser(param);
        return role;
    }

    @Override
    @Transactional
    public int updateUser(UserUpdateVo userUpdateVo) {

        User user_1 = this.userMapper.queryUserByUUID(userUpdateVo.getUuid());
        if (user_1 != null) {
            // 校验修改后的邮箱是否已存在
            User user_2 = this.userMapper.queryUserByEmail(userUpdateVo.getEmail());
            if (user_2.getUuid() != user_1.getUuid()) {
                throw new CustomerException("邮箱已存在");
            }

            // 校验修改后的用户名是否已存在
            User user_3 = this.userMapper.queryUserByNickname(userUpdateVo.getNickname());
            if (user_3.getUuid() == user_1.getUuid()) {
                throw new CustomerException("用户名已存在");
            }
        } else {
            throw new CustomerException("待修改用户不存在");
        }

        String password = null;
        if (userUpdateVo.getPassword() != null) {
            password = this.passwordEncoder.encode(userUpdateVo.getPassword());
        }

        Map<String, Object> param = BeanUtil.beanToMap(userUpdateVo);
        param.put("password",password);
        param.put("updateTime", new Date());

        int role = this.userMapper.updateUser(param);
        return role;
    }
}
