package com.aomsir.jewixapi.service;


import com.aomsir.jewixapi.pojo.entity.Tag;
import com.aomsir.jewixapi.pojo.vo.TagUpdateVo;
import com.aomsir.jewixapi.util.PageUtils;

import java.util.List;
import java.util.Map;

public interface TagService {

    PageUtils searchTagByPage(Map<String, Object> param);

    int addTagByName(String tagName);

    int updateTagById(TagUpdateVo updateVo);

    Tag searchTagById(Long tagId);

    PageUtils searchArticleListByTagName(Map<String, Object> param);

    int deleteTags(List<Long> tagIds);
}
