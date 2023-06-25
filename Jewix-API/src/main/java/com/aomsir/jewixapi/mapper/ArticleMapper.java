package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.dto.ArticleArchiveDTO;
import com.aomsir.jewixapi.pojo.dto.ArticleRandomDTO;
import com.aomsir.jewixapi.pojo.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Author: Aomsir
 * @Date: 2023/4/15
 * @Description: 文章Mapper
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Mapper
public interface ArticleMapper {
    /**
     * 根据标签名查询文章数量
     * @param tagName 标签名
     * @return 文章数量
     */
    Long queryArticleCountByTagName(@Param("tagName") String tagName);

    /**
     * 根据分类名查询文章数量
     * @param sonCategoryName 子分类名
     * @param parentCategoryId 父分类ID
     * @return 文章数量
     */
    Long queryArticleCountByCategoryName(@Param("categoryName") String sonCategoryName,
                                         @Param("parentCategoryId") Long parentCategoryId);

    /**
     * 查询后台文章数量
     * @param param 参数列表
     * @return 文章数量
     */
    int queryArticleBackendCount(@Param("param") Map<String, Object> param);

    /**
     * 查询后台文章列表
     * @param param 参数列表
     * @return 文章列表
     */
    ArrayList<Article> queryArticleBackendList(@Param("param") Map<String, Object> param);

    /**
     * 查询前台文章数量
     * @param param 参数列表
     * @return 文章数量
     */
    int queryArticleFrontCount(@Param("param") Map<String, Object> param);

    /**
     * 查询前台文章列表
     * @param param 参数列表
     * @return 文章数量
     */
    ArrayList<Article> queryArticleFrontList(@Param("param") Map<String, Object> param);

    /**
     * 插入文章
     * @param param 参数列表
     * @return 插入的文章主键值
     */
    Long insertArticle(@Param("param") Map<String, Object> param);

    /**
     * 插入文章与标签关联数据
     * @param articleId 文章id
     * @param tagId 标签id
     * @return 插入影响的行数
     */
    int insertArticleAndTag(@Param("articleId") Long articleId, @Param("tagId") Long tagId);

    /**
     * 插入文章与分类关联数据
     * @param articleId 文章id
     * @param categoryId 分类id
     * @return 插入影响的行数
     */
    int insertArticleAndCategory(@Param("articleId") Long articleId, @Param("categoryId") Long categoryId);

    /**
     * 插入文章与用户关联数据
     * @param articleId 文章id
     * @param userId 用户id
     * @return 插入所影响的行数
     */
    int insertArticleAndUser(@Param("articleId") Long articleId, @Param("userId") Long userId);

    /**
     * 根据文章UUID查询文章详情
     * @param uuid 文章uuid
     * @return 文章详情
     */
    Article queryArticleByUuid(@Param("uuid") String uuid);

    /**
     * 根据文章id查询文章详情
     * @param id 文章id
     * @return 文章详情
     */
    Article queryArticleById(@Param("id") Long id);

    /**
     * 更新文章
     * @param param 参数列表
     * @return 更新所影响的行数
     */
    int updateArticle(@Param("param") Map<String, Object> param);

    /**
     * 根据文章id查询文章所关联的分类列表
     * @param id 文章id
     * @return 分类列表
     */
    List<String> queryArticleCategoryNameList(@Param("id") Long id);

    /**
     * 根据文章id查询文章所关联的分类列表
     * @param id 文章id
     * @return 分类列表
     */
    List<String> queryArticleTagNameList(@Param("id") Long id);

    /**
     * 根据文章id查询作者信息
     * @param id 文章id
     * @return 作者名
     */
    String queryArticleUserName(@Param("id") Long id);

    /**
     * 查询上一篇的文章uuid
     * @param createTime 创建时间
     * @return 文章uuid
     */
    String queryLastUuidByCreateTime(@Param("createTime") Date createTime);

    /**
     * 查询下一篇文章的uuid
     * @param createTime 创建时间
     * @return 文章uuid
     */
    String queryNextUuidByCreateTime(@Param("createTime") Date createTime);

    /**
     * 根据文章id列表查询文章们的数量
     * @param ids 文章id列表
     * @return 文章数目
     */
    int queryArticleCountById(@Param("ids") List<Long> ids);

    /**
     * 逻辑删除文章
     * @param ids 文章id列表
     * @return 删除所影响的函数
     */
    int deleteArticlesByArchive(@Param("ids") List<Long> ids);

    /**
     * 物理删除文章
     * @param ids 文章id列表
     * @return 删除所影响的函数
     */
    int deleteArticlesByPhysics(@Param("ids") List<Long> ids);

    /**
     * 删除文章所引用的分类
     * @param ids 文章id列表
     * @return 所影响的行数
     */
    int deleteArticleOfCategories(@Param("ids") List<Long> ids);

    /**
     * 删除文章所引用的标签
     * @param ids 文章id列表
     * @return 所影响的行数
     */
    int deleteArticleOfTags(@Param("ids") List<Long> ids);

    /**
     * 删除文章所引用的用户信息
     * @param ids 文章id列表
     */
    void deleteArticleOfUser(@Param("ids") List<Long> ids);

    /**
     * 根据用户id查询所拥有的文章数目
     * @param id 用户id
     * @return 文章数量
     */
    int queryArticleCountByUserId(@Param("id") Long id);

    /**
     * 根据文章id查询文章所引用的标签列表
     * @param id 文章id
     * @return 标签id列表
     */
    List<Long> queryArticleTagIdList(@Param("id") Long id);

    /**
     * 根据文章id查询文章所引用的分类列表
     * @param id 文章id
     * @return 分类id列表
     */
    List<Long> queryArticleCategoryIdList(@Param("id") Long id);

    /**
     * 更新文章浏览量
     * @param id 文章id
     * @param i 新增的浏览量
     */
    void updateArticleViewCount(@Param("id") Long id, @Param("count") int i);

    /**
     * 根据分类id查询引用的文章数目
     * @param categoryId 分类id
     * @return 文章数量
     */
    Long queryArticleCountByCategoryId(@Param("categoryId") Long categoryId);

    /**
     * 查询文章id列表
     * @return 文章id列表
     */
    List<Long> queryArticleId();

    /**
     * 随机查询文章id列表
     * @param resultList 参数列表
     * @return 随机文章列表
     */
    List<ArticleRandomDTO> queryArticlesByRandomIds(@Param("list") List<Long> resultList);

    /**
     * 查询文章数目
     * @return 文章数目
     */
    Integer queryArticleCount();

    /**
     * 查询最新发表日期
     * @return 最新发表日期
     */
    Date queryLastActive();

    /**
     * 查询归档文章数目
     * @return 文章数量
     */
    Integer queryArticleCountByArchive();

    /**
     * 查询文章归档信息
     * @param param 参数列表
     * @return 文章归档信息
     */
    ArrayList<ArticleArchiveDTO> queryArticleByArchive(@Param("param") Map<String, Object> param);

    /**
     * 根据标签id查询文章详情
     * @param tagId 标签id
     * @return 文章详情
     */
    Article queryArticleByTagId(@Param("tagId") Long tagId);

    /**
     * 后台根据uuid查询文章详情
     * @param uuid 文章uuid
     * @return 文章详情
     */
    Article queryBackendArticleByUuid(@Param("uuid") String uuid);
}
