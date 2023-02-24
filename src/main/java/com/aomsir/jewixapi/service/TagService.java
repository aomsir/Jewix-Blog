package com.aomsir.jewixapi.service;


import com.aomsir.jewixapi.pojo.dto.ArticleTagDTO;
import com.aomsir.jewixapi.pojo.entity.Tag;
import com.aomsir.jewixapi.pojo.vo.TagUpdateVo;
import com.aomsir.jewixapi.utils.PageUtils;

import java.util.Map;

public interface TagService {

    PageUtils searchTagByPage(Map<String, Object> param);

    int addTagByName(String tagName);

    int updateTagById(TagUpdateVo updateVo);

    Tag searchTagById(Long tagId);

    ArticleTagDTO searchArticleListByTagName(String tagName);
}
