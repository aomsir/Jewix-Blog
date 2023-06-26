package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.LogMapper;
import com.aomsir.jewixapi.mapper.UserMapper;
import com.aomsir.jewixapi.pojo.dto.LoginLogDTO;
import com.aomsir.jewixapi.pojo.dto.OperateLogDTO;
import com.aomsir.jewixapi.pojo.entity.LoginLog;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.service.LogService;
import com.aomsir.jewixapi.util.NetUtils;
import com.aomsir.jewixapi.util.PageUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.aomsir.jewixapi.constant.CommonConstants.JSON_PARSE_ERROR;
import static com.aomsir.jewixapi.constant.CommonConstants.PARAMETER_ERROR;

/**
 * @Author: Aomsir
 * @Date: 2023/4/23
 * @Description: 日志业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Service
public class LogServiceImpl implements LogService {

    @Resource
    private NetUtils netUtils;

    @Resource
    private LogMapper logMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertLoginLog(HttpServletRequest req,Long userId) {
        String ip = this.netUtils.getRealIp(req);


        String location = null;
        try {
            location = this.netUtils.getLocationInfo(ip);
        } catch (JsonProcessingException e) {
            throw new CustomerException(JSON_PARSE_ERROR);
        }

        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(userId);
        loginLog.setIp(ip);
        loginLog.setLocation(location);
        loginLog.setOperateTime(new Date());

        this.logMapper.insertLoginLog(loginLog);
    }

    @Override
    public PageUtils searchLoginLogsByPage(Map<String, Object> param) {
        int count = this.logMapper.queryLoginLogCounts(param);

        List<LoginLogDTO> list = null;
        if (count > 0) {
            list = this.logMapper.queryLoginLogsByPage(param);
            for (LoginLogDTO loginLogDTO : list) {
                User user = this.userMapper.queryUserById(loginLogDTO.getUserId());
                loginLogDTO.setNickname(user.getNickname());
            }
        } else {
            list = new ArrayList<>();
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        return new PageUtils(list, count, start, length);
    }

    @Override
    public PageUtils searchOperateLogsByPage(Map<String, Object> param) {
        int count = this.logMapper.queryOperateLogCounts(param);

        List<OperateLogDTO> list = null;
        if (count > 0) {
            list = this.logMapper.queryOperateLogsByPage(param);
            for (OperateLogDTO loginLogDTO : list) {
                User user = this.userMapper.queryUserById(loginLogDTO.getUserId());
                loginLogDTO.setNickname(user.getNickname());
            }
        } else {
            list = new ArrayList<>();
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        return new PageUtils(list, count, start, length);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteLoginLogs(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new CustomerException(PARAMETER_ERROR);
        }
        return this.logMapper.deleteLoginLogs(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteOperateLogs(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new CustomerException(PARAMETER_ERROR);
        }
        return this.logMapper.deleteOperateLogs(ids);
    }
}
