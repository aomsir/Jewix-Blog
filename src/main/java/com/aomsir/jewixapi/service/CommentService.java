package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.utils.PageUtils;

import java.util.Map;

public interface CommentService {
    PageUtils searchBackendCommentListByPage(Map<String, Object> param);

    PageUtils searchFrontCommentListByPage(Map<String, Object> param);
}
