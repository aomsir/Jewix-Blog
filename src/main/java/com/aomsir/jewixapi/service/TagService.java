package com.aomsir.jewixapi.service;


import com.aomsir.jewixapi.pojo.vo.TagUpdateVo;
import com.aomsir.jewixapi.utils.PageUtils;

import java.util.Map;

public interface TagService {

    PageUtils searchTagByPage(Map<String, Object> param);

    int addTagByName(String tagName);

    int updateTagById(TagUpdateVo updateVo);
}
