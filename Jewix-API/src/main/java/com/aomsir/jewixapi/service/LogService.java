package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.util.PageUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/6/26
 * @Description: 日志业务接口
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface LogService {

    /**
     * 插入登录日志
     * @param req request请求对象
     * @param userId 用户id
     */
    void insertLoginLog(HttpServletRequest req,Long userId);


    /**
     * 分页查询登录日志
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchLoginLogsByPage(Map<String, Object> param);


    /**
     * 分页查询操作日志
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchOperateLogsByPage(Map<String, Object> param);


    /**
     * 删除登录日志
     * @param ids id列表
     * @return 删除所影响的行数
     */
    int deleteLoginLogs(List<Long> ids);


    /**
     * 删除操作日志
     * @param ids id列表
     * @return 删除所影响的行数
     */
    int deleteOperateLogs(List<Long> ids);
}
