package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.mapper.LogMapper;
import com.aomsir.jewixapi.pojo.entity.LoginLog;
import com.aomsir.jewixapi.service.LogService;
import com.aomsir.jewixapi.utils.HostHolder;
import com.aomsir.jewixapi.utils.NetUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

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

    @Override
    @Transactional
    public void insertLoginLog(HttpServletRequest req,Long userId) throws JsonProcessingException {
        String ip = this.netUtils.getRealIp(req);
        String location;
        if (ip.equals("127.0.0.1")) {
            location = "未知";
        } else {
            location = this.netUtils.getLocationInfo(ip);
        }


        LoginLog loginLog = new LoginLog();
        loginLog.setUserId(userId);
        loginLog.setIp(ip);
        loginLog.setLocation(location);
        loginLog.setOperateTime(new Date());

        this.logMapper.insertLoginLog(loginLog);
    }
}
