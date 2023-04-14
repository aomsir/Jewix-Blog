package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.vo.CommentAddVo;
import com.aomsir.jewixapi.pojo.vo.CommentDeleteVo;
import com.aomsir.jewixapi.pojo.vo.CommentUpdateStatusVo;
import com.aomsir.jewixapi.pojo.vo.CommentUpdateVo;
import com.aomsir.jewixapi.utils.PageUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface CommentService {
    PageUtils searchBackendCommentListByPage(Map<String, Object> param);

    PageUtils searchFrontCommentListByPage(Map<String, Object> param);

    int addComment(CommentAddVo commentAddVo, HttpServletRequest request) throws JsonProcessingException;

    int updateComment(CommentUpdateVo commentUpdateVo);

    int deleteComment(List<Long> list);

    int updateCommentStatus(CommentUpdateStatusVo commentUpdateStatusVo);
}
