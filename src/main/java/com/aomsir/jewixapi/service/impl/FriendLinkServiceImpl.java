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
        PageUtils pageUtils = new PageUtils(list, count, start,length);
        return pageUtils;
    }

    @Override
    public int addFriendLink(Map<String, Object> param) {
        FriendLink friendLink = this.friendLinkMapper.queryFriendLinkByTitle((String) param.get("title"));

        if (friendLink != null) {
            throw new CustomerException("当前友情链接已经存在了嗷!");
        }

        param.put("createTime",new Date());
        param.put("updateTime", new Date());
        int role = this.friendLinkMapper.insertFriendLink(param);
        return role;
    }

    @Override
    public int updateFriendLink(Map<String, Object> param) {
        FriendLink friendLink = this.friendLinkMapper.queryFriendLinkById((Integer) param.get("id"));
        if (friendLink == null) {
            throw new CustomerException("待修改的友情链接不存在嗷!");
        }

        param.put("updateTime", new Date());

        int role = this.friendLinkMapper.updateFriendLink(param);
        return role;
    }

    @Override
    public FriendLink findFriendLinkInfoById(Integer id) {
        if (id == null || id < 1) {
            throw new CustomerException("友情链接不存在嗷");
        }

        FriendLink friendLink = this.friendLinkMapper.queryFriendLinkById(id);
        return friendLink;
    }

    @Override
    @Transactional
    public int deleteFriendLinks(List<Integer> ids) {
        if (ids == null || ids.size() == 0) {
            throw new CustomerException("参数异常");
        }

        return this.friendLinkMapper.deleteFriendLinks(ids);
    }
}
