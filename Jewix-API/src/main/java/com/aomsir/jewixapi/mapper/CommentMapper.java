package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper {
    Integer queryCommentBackendCount(@Param("param") Map<String, Object> param);

    ArrayList<Comment> queryCommentBackendPageList(@Param("param") Map<String, Object> param);

    Integer queryCommentFrontParentCount(@Param("param") Map<String, Object> param);

    ArrayList<Comment> queryCommentFrontPageList(@Param("param") Map<String, Object> param);

    Comment queryCommentByParentId(@Param("parentId") Long parentId);

    Comment queryCommentById(@Param("id") Long id);

    int updateComment(@Param("param") Map<String, Object> param);

    Comment queryCommentByPermId(@Param("permId") Long permId);

    int insertComment(@Param("param") Map<String, Object> param);

    List<Comment> queryCommentsByPermId(@Param("permId") Long id);

    List<Comment> queryCommentsByParentId(@Param("parentId") Long id);

    int deleteComment(@Param("id") Long id);

    int updateCommentStatus(@Param("param") Map<String, Object> param);

    void deleteCommentByPageId(@Param("pageId") Integer pageId, @Param("type") Integer type);

    Integer queryCommentCount();

    Integer queryCommentCountByArchive();

    List<Long> queryCommentIdsByTypeAndTargetId(@Param("id") Long id, @Param("type") Integer type);

    void deleteCommentByIds(@Param("ids") List<Long> commentIds);

    Long queryArticleAuthorId(@Param("targetId") Long targetId);
}
