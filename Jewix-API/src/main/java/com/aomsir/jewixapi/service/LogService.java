package com.aomsir.jewixapi.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import javax.servlet.http.HttpServletRequest;

public interface LogService {
    void insertLoginLog(HttpServletRequest req,Long userId) throws JsonProcessingException;
}
