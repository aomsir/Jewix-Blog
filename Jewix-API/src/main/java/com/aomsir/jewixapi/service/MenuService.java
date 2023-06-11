package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.util.PageUtils;

import java.util.Map;

public interface MenuService {
    PageUtils searchMenusByPage(Map<String, Object> param);
}
