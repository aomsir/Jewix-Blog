package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.entity.FriendLink;
import com.aomsir.jewixapi.utils.PageUtils;

import java.util.List;
import java.util.Map;

public interface FriendLinkService {
    PageUtils searchFriendLinkListByPage(Map<String, Object> param);

    int addFriendLink(Map<String, Object> param);

    int updateFriendLink(Map<String, Object> param);

    FriendLink searchFriendLinkInfoById(Integer id);

    int deleteFriendLinks(List<Integer> ids);
}
