package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.vo.CommentAddVo;
import com.aomsir.jewixapi.utils.PageUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface CommentService {
    PageUtils searchBackendCommentListByPage(Map<String, Object> param);

    PageUtils searchFrontCommentListByPage(Map<String, Object> param);

    int addComment(CommentAddVo commentAddVo, HttpServletRequest request);
}
