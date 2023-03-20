package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface CommentMapper {
    int queryCommentBackendCount(@Param("param") Map<String, Object> param);

    ArrayList<Comment> queryCommentBackendPageList(@Param("param") Map<String, Object> param);

    int queryCommentFrontParentCount(@Param("param") Map<String, Object> param);

    ArrayList<Comment> queryCommentFrontPageList(@Param("param") Map<String, Object> param);

    Comment queryCommentByParentIdOrPermId(@Param("param") Map<String, Object> param1);
}
