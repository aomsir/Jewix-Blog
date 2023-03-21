package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface CommentMapper {
    int queryCommentBackendCount(@Param("param") Map<String, Object> param);

    ArrayList<Comment> queryCommentBackendPageList(@Param("param") Map<String, Object> param);

    int queryCommentFrontParentCount(@Param("param") Map<String, Object> param);

    ArrayList<Comment> queryCommentFrontPageList(@Param("param") Map<String, Object> param);

    Comment queryCommentByParentId(@Param("parentId") Long parentId);

    Comment queryCommentById(@Param("id") Long id);

    int updateComment(@Param("param") Map<String, Object> param);

    Comment queryCommentByPermId(@Param("permId") Long permId);

    int insertComment(@Param("param") Map<String, Object> param);

    List<Comment> queryCommentsByPermId(@Param("id") Long id);

    List<Comment> queryCommentsByParentId(@Param("id") Long id);

    int deleteComment(@Param("id") Long id);

    int updateCommentStatus(@Param("param") Map<String, Object> param);
}
