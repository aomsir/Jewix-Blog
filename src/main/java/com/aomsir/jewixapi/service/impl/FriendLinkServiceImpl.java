package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.mapper.FriendLinkMapper;
import com.aomsir.jewixapi.pojo.entity.FriendLink;
import com.aomsir.jewixapi.service.FriendLinkService;
import com.aomsir.jewixapi.utils.PageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
}
