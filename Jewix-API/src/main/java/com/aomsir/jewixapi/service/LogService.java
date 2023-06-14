package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.util.PageUtils;
import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface LogService {
    void insertLoginLog(HttpServletRequest req,Long userId) throws JsonProcessingException;

    PageUtils searchLoginLogsByPage(Map<String, Object> param);

    PageUtils searchOperateLogsByPage(Map<String, Object> param);

    int deleteLoginLogs(List<Long> ids);

    int deleteOperateLogs(List<Long> ids);
}
