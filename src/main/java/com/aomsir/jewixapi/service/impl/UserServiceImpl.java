package com.aomsir.jewixapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.ArticleMapper;
import com.aomsir.jewixapi.mapper.UserMapper;
import com.aomsir.jewixapi.pojo.dto.UserConfigDTO;
import com.aomsir.jewixapi.pojo.entity.Tag;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.pojo.vo.*;
import com.aomsir.jewixapi.service.UserService;
import com.aomsir.jewixapi.utils.HostHolder;
import com.aomsir.jewixapi.utils.JwtUtils;
import com.aomsir.jewixapi.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

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
    private ArticleMapper articleMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private HostHolder hostHolder;


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
        String password = this.passwordEncoder.encode(userAddVo.getPassword());

        Map<String, Object> param = BeanUtil.beanToMap(userAddVo);
        param.put("createTime",new Date());
        param.put("updateTime",new Date());
        param.put("password", password);
        param.put("uuid", UUID.randomUUID().toString());
        param.put("salt",UUID.randomUUID().toString());
        param.put("status",1);

        return this.userMapper.insertUser(param);
    }

    @Override
    @Transactional
    public int updateUser(UserUpdateVo userUpdateVo) {
        User user_1 = this.userMapper.queryUserByUUID(userUpdateVo.getUuid());

        if (user_1 == null) {
            throw new CustomerException("用户不存在");
        }

        // 邮箱已被使用(提交邮箱与库中该用户不一致才可)
        if (!Objects.equals(user_1.getEmail(), userUpdateVo.getEmail())) {
            User user = this.userMapper.queryUserByEmail(userUpdateVo.getEmail());
            if (user != null) {
                throw new CustomerException("邮箱已存在嗷");
            }
        }

        // 用户名已被使用
        if (!Objects.equals(user_1.getNickname(),userUpdateVo.getNickname())) {
            User user = this.userMapper.queryUserByNickname(userUpdateVo.getNickname());
            if (user != null) {
                throw new CustomerException("用户名已经存在嗷");
            }
        }

        if (StrUtil.isNotEmpty(userUpdateVo.getPassword())) {
            userUpdateVo.setPassword(this.passwordEncoder.encode(userUpdateVo.getPassword()));
        }

        Map<String, Object> param = BeanUtil.beanToMap(userUpdateVo);
        param.put("updateTime",new Date());
        return this.userMapper.updateUser(param);
    }


    @Override
    public int hasUser(UserHaveVo userHaveVo) {
        if (userHaveVo.getEmail() == null && userHaveVo.getNickname() == null) {
            throw new CustomerException("用户名/邮箱参数未携带");
        }

        return this.userMapper.queryUserByEmailOrNickname(userHaveVo);
    }

    @Override
    @Transactional
    public int updateStatus(UserStatusVo userStatusVo) {
        User user = this.userMapper.queryUserByUUID(userStatusVo.getUuid());
        if (user == null) {
            throw new CustomerException("用户不存在");
        }

        return this.userMapper.updateUserStatus(userStatusVo);
    }

    @Override
    public User searchCurrentUser() {

        Long userId = this.hostHolder.getUserId();
        if (userId == null) {
            throw new CustomerException("登录凭证失效,请重新登录");
        }
        User user = (User) this.redisTemplate.opsForValue().get("user:info:" + userId);
        if (user == null) {
            this.redisTemplate.delete("user:token:" + userId);
            throw new CustomerException("登录凭证失效,请重新登录");
        }

        return user;
    }

    @Override
    @Transactional
    public int deleteUserByArchive(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
             throw new CustomerException("参数异常");
        }


        List<Long> trueIds = new ArrayList<>();
        for (Long id : ids) {
            if (Objects.equals(this.hostHolder.getUserId(), id)) {
                throw new CustomerException("不允许删除自己");
            }

            // 查看有无文章引用
            if (this.articleMapper.queryArticleCountByUserId(id) == 0) {
                trueIds.add(id);
            } else {
                throw new CustomerException("用户id为" + id + "的用户分类下有文章,请先删除");
            }
        }

        return this.userMapper.deleteUserByArchive(ids);
    }

    @Override
    @Transactional
    public int deleteUserByPhysics(List<Long> ids) {
        if (ids == null || ids.size() == 0) {
            throw new CustomerException("参数异常");
        }

        List<Long> trueIds = new ArrayList<>();
        for (Long id : ids) {
            // 查看有无文章引用
            if (this.articleMapper.queryArticleCountByUserId(id) == 0) {
                trueIds.add(id);
            } else {
                throw new CustomerException("用户id为" + id + "的用户分类下有文章,请先删除");
            }
        }

        return this.userMapper.deleteUserByPhysics(ids);
    }

    @Override
    @Transactional
    public int updateUserByMyself(UserUpdateVo userUpdateVo) {
        User user_1 = this.userMapper.queryUserByUUID(userUpdateVo.getUuid());
        if (user_1 == null) {
            throw new CustomerException("用户不存在");
        }

        if (!Objects.equals(user_1.getId(), this.hostHolder.getUserId())) {
            throw new CustomerException("非当前登录用户");
        }

        // 邮箱已被使用(提交邮箱与库中该用户不一致才可)
        if (!Objects.equals(user_1.getEmail(), userUpdateVo.getEmail())) {
            User user = this.userMapper.queryUserByEmail(userUpdateVo.getEmail());
            if (user != null) {
                throw new CustomerException("邮箱已存在嗷");
            }
        }

        // 用户名已被使用
        if (!Objects.equals(user_1.getNickname(),userUpdateVo.getNickname())) {
            User user = this.userMapper.queryUserByNickname(userUpdateVo.getNickname());
            if (user != null) {
                throw new CustomerException("用户名已经存在嗷");
            }
        }

        if (StrUtil.isNotEmpty(userUpdateVo.getPassword())) {
            userUpdateVo.setPassword(this.passwordEncoder.encode(userUpdateVo.getPassword()));
        }

        Map<String, Object> param = BeanUtil.beanToMap(userUpdateVo);
        param.put("updateTime",new Date());
        return this.userMapper.updateUser(param);
    }
}
