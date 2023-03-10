package com.aomsir.jewixapi.mapper;


import com.aomsir.jewixapi.pojo.dto.ArticlePreviewDTO;
import com.aomsir.jewixapi.pojo.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Mapper
public interface TagMapper {

    Long queryTagCount();

    ArrayList<Tag> queryTagListByPage(@Param("param") Map<String, Object> param);

    Tag queryTagByName(@Param("tagName") String tagName);

    int insertTag(@Param("tag") Tag tag);

    Tag queryTagById(@Param("id") Long id);

    int updateTagById(@Param("tag") Tag tag_2);

    List<ArticlePreviewDTO> queryArticleListByTagName(@Param("param") Map<String, Object> param);

    Boolean queryIdsExists(@Param("lisi") List<Integer> tagIds);
}
