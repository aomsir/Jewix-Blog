package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.utils.PageUtils;

import java.util.Map;

public interface FriendLinkService {
    PageUtils searchFriendLinkListByPage(Map<String, Object> param);
}
