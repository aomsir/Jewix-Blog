package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/4/15
 * @Description: 评论Mapper
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Mapper
public interface CommentMapper {
    /**
     * 后台查询评论数量
     * @param param 参数列表
     * @return 评论数量
     */
    Integer queryCommentBackendCount(@Param("param") Map<String, Object> param);

    /**
     * 后台分页查询评论列表
     * @param param 参数列表
     * @return 评论列表
     */
    ArrayList<Comment> queryCommentBackendPageList(@Param("param") Map<String, Object> param);

    /**
     * 前台查询父评论数量
     * @param param 参数列表
     * @return 父评论数量
     */
    Integer queryCommentFrontParentCount(@Param("param") Map<String, Object> param);

    /**
     * 前台分页查询评论列表
     * @param param 参数列表
     * @return 评论列表
     */
    ArrayList<Comment> queryCommentFrontPageList(@Param("param") Map<String, Object> param);

    /**
     * 根据父id查询评论详情
     * @param parentId 父评论id
     * @return 评论详情
     */
    Comment queryCommentByParentId(@Param("parentId") Long parentId);

    /**
     * 根据id查询评论详情
     * @param id 评论id
     * @return 评论详情
     */
    Comment queryCommentById(@Param("id") Long id);

    /**
     * 更新评论
     * @param param 参数列表
     * @return 更新评论所影响的行数
     */
    int updateComment(@Param("param") Map<String, Object> param);

    /**
     * 根据一级评论id查询评论详情
     * @param permId 一级id
     * @return 评论详情
     */
    Comment queryCommentByPermId(@Param("permId") Long permId);

    /**
     * 插入评论
     * @param param 参数列表
     * @return 插入评论所影响的行数
     */
    int insertComment(@Param("param") Map<String, Object> param);

    /**
     * 根据一级id查询评论列表
     * @param id 一级id
     * @return 评论列表
     */
    List<Comment> queryCommentsByPermId(@Param("permId") Long id);

    /**
     * 根据父id查询评论列表
     * @param id 父id
     * @return 评论列表
     */
    List<Comment> queryCommentsByParentId(@Param("parentId") Long id);

    /**
     * 删除评论
     * @param id 评论id
     * @return 删除所影响的行数
     */
    int deleteComment(@Param("id") Long id);

    /**
     * 更新评论状态
     * @param param 参数列表
     * @return 更新所影响的行数
     */
    int updateCommentStatus(@Param("param") Map<String, Object> param);

    /**
     * 根据页面id删除评论
     * @param pageId 页面id
     * @param type 类型
     */
    void deleteCommentByPageId(@Param("pageId") Integer pageId, @Param("type") Integer type);

    /**
     * 查询评论数量
     * @return 评论数量
     */
    Integer queryCommentCount();

    /**
     * 归档查询评论数量
     * @return 评论数量
     */
    Integer queryCommentCountByArchive();

    /**
     * 根据目标id与类型查询评论id列表
     * @param id 目标id
     * @param type 类型
     * @return 评论id列表
     */
    List<Long> queryCommentIdsByTypeAndTargetId(@Param("id") Long id, @Param("type") Integer type);

    /**
     * 根据评论id列表删除评论
     * @param commentIds 评论id列表
     */
    void deleteCommentByIds(@Param("ids") List<Long> commentIds);

    /**
     * 查询文章作者id
     * @param targetId 文章id
     * @return 作者id
     */
    Long queryArticleAuthorId(@Param("targetId") Long targetId);
}
