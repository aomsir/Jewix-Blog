package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface ArticleMapper {
    public Long queryArticleCountByTagName(@Param("tagName") String tagName);

    Long queryArticleCountByCategoryName(@Param("categoryName") String categoryName);

    int queryArticleBackendCount(@Param("param") Map<String, Object> param);

    ArrayList<Article> queryArticleBackendList(@Param("param") Map<String, Object> param);

    int queryArticleFrontCount(@Param("param") Map<String, Object> param);

    ArrayList<Article> queryArticleFrontList(@Param("param") Map<String, Object> param);

    int insertArticle(@Param("param") Map<String, Object> param);

    int insertArticleAndTag(@Param("articleId") int articleId, @Param("tagId") Integer tagId);

    int insertArticleAndCategory(@Param("articleId") int articleId, @Param("categoryId") Integer categoryId);

    int insertArticleAndUser(@Param("articleId") int articleId, @Param("userId") Long userId);
}
