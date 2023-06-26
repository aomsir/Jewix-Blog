package com.aomsir.jewixapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.ArticleMapper;
import com.aomsir.jewixapi.mapper.UserMapper;
import com.aomsir.jewixapi.pojo.dto.CurrentUserDTO;
import com.aomsir.jewixapi.pojo.dto.UserConfigDTO;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.pojo.vo.*;
import com.aomsir.jewixapi.service.UserService;
import com.aomsir.jewixapi.util.UserHolder;
import com.aomsir.jewixapi.util.PageUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static com.aomsir.jewixapi.constant.CommonConstants.PARAMETER_ERROR;
import static com.aomsir.jewixapi.constant.CommonConstants.TICKET_ERROR;
import static com.aomsir.jewixapi.constant.RedisConstants.*;
import static com.aomsir.jewixapi.constant.UserConstants.*;

/**
 * @Author: Aomsir
 * @Date: 2023/2/19
 * @Description: 用户业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private UserHolder userHolder;


    @Override
    public User searchUserByUuid(String uuid) {
        if (uuid == null) {
            throw new CustomerException(PARAMETER_ERROR);
        }

        User user = this.userMapper.queryUserByUuid(uuid);

        return user;
    }

    @Override
    public UserConfigDTO searchConfigUser() {
        UserConfigDTO userConfigDTO = this.userMapper.queryConfigUser();
        if (userConfigDTO == null) {
            throw new CustomerException(USER_IS_NULL);
        }
        return userConfigDTO;
    }

    @Override
    public PageUtils searchUserByPage(Map<String, Object> param) {
        Long count = this.userMapper.queryUserCount((Integer) param.get("status"),
                (String)param.get("email"));
        ArrayList<User> list = null;
        if (count > 0) {
            list = this.userMapper.queryUserListByPage(param);
        } else {
            list = new ArrayList<>();
        }
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        return new PageUtils(list,count,start,length);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addUser(UserAddVo userAddVo) {
        User user1 = this.userMapper.queryUserByEmail(userAddVo.getEmail());
        User user2 = this.userMapper.queryUserByNickname(userAddVo.getNickname());

        if (user1 != null) {
            throw new CustomerException(USER_EMAIL_HAS_EXISTED);
        }

        if (user2 != null) {
            throw new CustomerException(USER_NAME_HAS_EXISTED);
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
    @Transactional(rollbackFor = Exception.class)
    public int updateUser(UserUpdateVo userUpdateVo) {
        User user1 = this.userMapper.queryUserByUuid(userUpdateVo.getUuid());

        if (user1 == null) {
            throw new CustomerException(USER_IS_NULL);
        }

        // 邮箱已被使用(提交邮箱与库中该用户不一致才可)
        if (!Objects.equals(user1.getEmail(), userUpdateVo.getEmail())) {
            User user = this.userMapper.queryUserByEmail(userUpdateVo.getEmail());
            if (user != null) {
                throw new CustomerException(USER_EMAIL_HAS_EXISTED);
            }
        }

        // 用户名已被使用
        if (!Objects.equals(user1.getNickname(),userUpdateVo.getNickname())) {
            User user = this.userMapper.queryUserByNickname(userUpdateVo.getNickname());
            if (user != null) {
                throw new CustomerException(USER_NAME_HAS_EXISTED);
            }
        }

        if (StrUtil.isNotEmpty(userUpdateVo.getPassword())) {
            userUpdateVo.setPassword(this.passwordEncoder.encode(userUpdateVo.getPassword()));
        }

        Map<String, Object> param = BeanUtil.beanToMap(userUpdateVo);
        param.put("updateTime",new Date());

        // 删除缓存
        if (user1.getId() == 10000L) {
            this.redisTemplate.delete(WEB_CONFIG_KEY);
        }
        return this.userMapper.updateUser(param);
    }


    @Override
    public int hasUser(UserHaveVo userHaveVo) {
        if (userHaveVo.getEmail() == null && userHaveVo.getNickname() == null) {
            throw new CustomerException(PARAMETER_ERROR);
        }

        return this.userMapper.queryUserByEmailOrNickname(userHaveVo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateStatus(UserStatusVo userStatusVo) {
        User user = this.userMapper.queryUserByUuid(userStatusVo.getUuid());
        if (user == null) {
            throw new CustomerException(USER_IS_NULL);
        }

        if (user.getId().equals(this.userHolder.getUserId())) {
            throw new CustomerException(USER_CAN_NOT_UPDATE_STATUS_THEMSELVES);
        }

        return this.userMapper.updateUserStatus(userStatusVo);
    }

    @Override
    public CurrentUserDTO searchCurrentUser() {
        Long userId = this.userHolder.getUserId();
        if (userId == null) {
            throw new CustomerException(TICKET_ERROR);
        }
        Map<Object, Object> userMap = this.redisTemplate.opsForHash()
                .entries(USER_INFO_KEY + userId);

        if (userMap.isEmpty()) {
            this.redisTemplate.delete(USER_TOKEN_KEY + userId);
            throw new CustomerException(TICKET_ERROR);
        }

        return BeanUtil.toBean(userMap, CurrentUserDTO.class);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserByArchive(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
             throw new CustomerException(PARAMETER_ERROR);
        }


        List<Long> trueIds = new ArrayList<>();
        for (Long id : ids) {
            if (Objects.equals(this.userHolder.getUserId(), id)) {
                throw new CustomerException(USER_DELETE_MYSELF_IS_NOT_ALLOWED);
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
    @Transactional(rollbackFor = Exception.class)
    public int deleteUserByPhysics(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new CustomerException(PARAMETER_ERROR);
        }

        List<Long> trueIds = new ArrayList<>();
        for (Long id : ids) {
            // 查看有无文章引用
            if (this.articleMapper.queryArticleCountByUserId(id) == 0) {
                trueIds.add(id);
            } else {
                throw new CustomerException("id为" + id + "的用户分类下有文章,请先删除");
            }

            // 查询是有是自己
            User user = this.userMapper.queryUserById(id);
            if (user.getId().equals(this.userHolder.getUserId())) {
                throw new CustomerException(USER_CAN_NOT_DELETE_THEMSELVES);
            }
        }

        return this.userMapper.deleteUserByPhysics(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserByMyself(UserUpdateVo userUpdateVo) {
        User user1 = this.userMapper.queryUserByUuid(userUpdateVo.getUuid());
        if (user1 == null) {
            throw new CustomerException(USER_IS_NULL);
        }

        if (!Objects.equals(user1.getId(), this.userHolder.getUserId())) {
            throw new CustomerException(USER_IS_NOT_CURRENT);
        }

        // 邮箱已被使用(提交邮箱与库中该用户不一致才可)
        if (!Objects.equals(user1.getEmail(), userUpdateVo.getEmail())) {
            User user = this.userMapper.queryUserByEmail(userUpdateVo.getEmail());
            if (user != null) {
                throw new CustomerException(USER_EMAIL_HAS_EXISTED);
            }
        }

        // 用户名已被使用
        if (!Objects.equals(user1.getNickname(),userUpdateVo.getNickname())) {
            User user = this.userMapper.queryUserByNickname(userUpdateVo.getNickname());
            if (user != null) {
                throw new CustomerException(USER_NAME_HAS_EXISTED);
            }
        }

        if (StrUtil.isNotEmpty(userUpdateVo.getPassword())) {
            userUpdateVo.setPassword(this.passwordEncoder.encode(userUpdateVo.getPassword()));
        }

        Map<String, Object> param = BeanUtil.beanToMap(userUpdateVo);
        param.put("updateTime",new Date());

        // 删除缓存
        if (user1.getId() == 10000L) {
            this.redisTemplate.delete(WEB_CONFIG_KEY);
        }

        this.redisTemplate.delete(USER_INFO_KEY + user1.getId());
        this.redisTemplate.delete(USER_TOKEN_KEY + user1.getId());
        return this.userMapper.updateUser(param);
    }
}
