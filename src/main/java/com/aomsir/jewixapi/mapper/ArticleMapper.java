package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper {
    public Long queryArticleCountByTagName(@Param("tagName") String tagName);

    Long queryArticleCountByCategoryName(@Param("categoryName") String sonCategoryName,
                                         @Param("parentCategoryId") Long parentCategoryId);

    int queryArticleBackendCount(@Param("param") Map<String, Object> param);

    ArrayList<Article> queryArticleBackendList(@Param("param") Map<String, Object> param);

    int queryArticleFrontCount(@Param("param") Map<String, Object> param);

    ArrayList<Article> queryArticleFrontList(@Param("param") Map<String, Object> param);

    Long insertArticle(@Param("param") Map<String, Object> param);

    int insertArticleAndTag(@Param("articleId") Long articleId, @Param("tagId") Long tagId);

    int insertArticleAndCategory(@Param("articleId") Long articleId, @Param("categoryId") Long categoryId);

    int insertArticleAndUser(@Param("articleId") Long articleId, @Param("userId") Long userId);

    Article queryArticleByUuid(@Param("uuid") String uuid);

    Article queryArticleById(@Param("id") Long id);

    int updateArticle(@Param("param") Map<String, Object> param);

    List<String> queryArticleCategoryNameList(@Param("id") Long id);

    List<String> queryArticleTagNameList(@Param("id") Long id);

    String queryArticleUserName(@Param("id") Long id);

    String queryLastUuidByCreateTime(@Param("createTime") Date createTime);

    String queryNextUuidByCreateTime(@Param("createTime") Date createTime);

    int queryArticleCountById(@Param("ids") List<Long> ids);

    int deleteArticlesByArchive(@Param("ids") List<Long> ids);

    int deleteArticlesByPhysics(@Param("ids") List<Long> ids);

    int deleteArticleOfCategories(@Param("ids") List<Long> ids);

    int deleteArticleOfTags(@Param("ids") List<Long> ids);

    void deleteArticleOfUser(@Param("ids") List<Long> ids);

    int queryArticleCountByUserId(@Param("id") Long id);

    List<Long> queryArticleTagIdList(@Param("id") Long id);

    List<Long> queryArticleCategoryIdList(@Param("id") Long id);

    void updateArticleViewCount(@Param("id") Long id, @Param("count") int i);

    Long queryArticleCountByCategoryId(@Param("categoryId") Long categoryId);
}
