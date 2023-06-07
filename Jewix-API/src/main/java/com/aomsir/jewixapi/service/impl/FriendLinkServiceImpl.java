package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.FriendLinkMapper;
import com.aomsir.jewixapi.pojo.entity.FriendLink;
import com.aomsir.jewixapi.service.FriendLinkService;
import com.aomsir.jewixapi.util.PageUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.aomsir.jewixapi.constant.CommonConstants.PARAMETER_ERROR;
import static com.aomsir.jewixapi.constant.FriendLinkConstants.*;
import static com.aomsir.jewixapi.constant.RedisConstants.FRIEND_LINK_LIST_EXPIRE;
import static com.aomsir.jewixapi.constant.RedisConstants.FRIEND_LINK_LIST_KEY;

/**
 * @Author: Aomsir
 * @Date: 2023/2/26
 * @Description: 友情链接业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Service
public class FriendLinkServiceImpl implements FriendLinkService {

    @Resource
    private FriendLinkMapper friendLinkMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public PageUtils searchFriendLinkListByPage(Map<String, Object> param) {
        // 缓存命中则直接返回
        int start_1 = (int) param.get("start");

        int type_1 = 0;
        if (param.get("location") != null) {
            type_1 = (int) param.get("location");
        }


        if (start_1 == 0 && type_1 == 1) {
            PageUtils pageUtils = (PageUtils) this.redisTemplate.opsForValue().get(FRIEND_LINK_LIST_KEY);
            if (pageUtils != null) {
                return pageUtils;
            }
        }

        int count = this.friendLinkMapper.queryFriendLinkCount((Integer) param.get("location"));
        ArrayList<FriendLink> list = null;
        if (count > 0) {
            list = this.friendLinkMapper.queryFriendLinkByPage(param);
        } else {
            list = new ArrayList<>();
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");

        PageUtils pageUtils = new PageUtils(list, count, start, length);

        // 封装进Redis
        if (start_1 == 0 && type_1 == 1) {
            this.redisTemplate.opsForValue()
                    .set(FRIEND_LINK_LIST_KEY, pageUtils, FRIEND_LINK_LIST_EXPIRE, TimeUnit.DAYS);
        }
        return pageUtils;
    }

    @Override
    @Transactional
    public int addFriendLink(Map<String, Object> param) {
        FriendLink friendLink_1 = this.friendLinkMapper.queryFriendLinkByTitle((String) param.get("title"));
        FriendLink friendLink_2 = this.friendLinkMapper.queryFriendLinkByLink((String) param.get("link"));

        if (friendLink_1 != null) {
            throw new CustomerException(FRIEND_LINK_NAME_HAS_EXISTED);
        }

        if (friendLink_2 != null) {
            throw new CustomerException(FRIEND_LINK_LINK_HAS_EXISTED);
        }

        param.put("createTime",new Date());
        param.put("updateTime", new Date());

        // 删除首页缓存
        this.redisTemplate.delete(FRIEND_LINK_LIST_KEY);
        return this.friendLinkMapper.insertFriendLink(param);
    }

    @Override
    @Transactional
    public int updateFriendLink(Map<String, Object> param) {
        FriendLink friendLink = this.friendLinkMapper.queryFriendLinkById((Integer) param.get("id"));
        if (friendLink == null) {
            throw new CustomerException(FRIEND_LINK_IS_NULL);
        }

        param.put("updateTime", new Date());

        int role = this.friendLinkMapper.updateFriendLink(param);
        this.redisTemplate.delete(FRIEND_LINK_LIST_KEY);
        return role;
    }

    @Override
    public FriendLink searchFriendLinkInfoById(Integer id) {
        if (id == null || id < 1) {
            throw new CustomerException(FRIEND_LINK_IS_NULL);
        }

        return this.friendLinkMapper.queryFriendLinkById(id);
    }

    @Override
    @Transactional
    public int deleteFriendLinks(List<Integer> ids) {
        if (ids == null || ids.size() == 0) {
            throw new CustomerException(PARAMETER_ERROR);
        }

        int role = this.friendLinkMapper.deleteFriendLinks(ids);

        // 删除缓存
        this.redisTemplate.delete(FRIEND_LINK_LIST_KEY);
        return role;
    }
}
