package com.aomsir.jewixapi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleMapper {
    public Long queryArticleCountByTagName(@Param("tagName") String tagName);
}
