package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.FriendLinkMapper;
import com.aomsir.jewixapi.pojo.entity.FriendLink;
import com.aomsir.jewixapi.service.FriendLinkService;
import com.aomsir.jewixapi.utils.PageUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.aomsir.jewixapi.constants.CommonConstants.PARAMETER_ERROR;
import static com.aomsir.jewixapi.constants.FriendLinkConstants.*;

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

    @Override
    public PageUtils searchFriendLinkListByPage(Map<String, Object> param) {
        int count = this.friendLinkMapper.queryFriendLinkCount((Integer) param.get("location"));

        ArrayList<FriendLink> list = null;
        if (count > 0) {
            list = this.friendLinkMapper.queryFriendLinkByPage(param);
        } else {
            list = new ArrayList<>();
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        return new PageUtils(list, count, start,length);
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

        return this.friendLinkMapper.deleteFriendLinks(ids);
    }
}
