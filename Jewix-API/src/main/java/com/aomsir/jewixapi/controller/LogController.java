package com.aomsir.jewixapi.controller;


import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.annotation.OperateLog;
import com.aomsir.jewixapi.pojo.vo.LoginLogPageVo;
import com.aomsir.jewixapi.pojo.vo.OperateLogPageVo;
import com.aomsir.jewixapi.service.LogService;
import com.aomsir.jewixapi.util.PageUtils;
import com.aomsir.jewixapi.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/4/23
 * @Description: 日志控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@RestController
@Api(tags = "日志控制器")
public class LogController {
    @Resource
    private LogService logService;


    /**
     * 分页获取登录日志
     * @param loginLogPageVo 登录登录日志VO对象
     * @return 分页数据
     */
    @PreAuthorize("hasAuthority('ADMIN_LOG_LOGIN_LIST')")
    @ApiOperation(value = "分页获取登录日志")
    @GetMapping("/admin/logs/login")
    public R getLoginLogsByPage(@Validated LoginLogPageVo loginLogPageVo) {
        int page = loginLogPageVo.getPage();
        int length = loginLogPageVo.getLength();
        int start = (page - 1) * length;
        Map<String, Object> param = BeanUtil.beanToMap(loginLogPageVo);
        param.put("start", start);

        PageUtils pageUtils = this.logService.searchLoginLogsByPage(param);
        return R.ok()
                .put("result", pageUtils);
    }

    /**
     * 分页获取操作日志
     * @param operateLogPageVo 分页操作日志VO对象
     * @return 分页数据
     */
    @PreAuthorize("hasAuthority('ADMIN_LOG_OPERATE_LIST')")
    @ApiOperation(value = "分页获取操作日志")
    @GetMapping("/admin/logs/operate")
    public R getOperateLogsByPage(@Validated OperateLogPageVo operateLogPageVo) {

        int page = operateLogPageVo.getPage();
        int length = operateLogPageVo.getLength();
        int start = (page - 1) * length;

        Map<String, Object> param = BeanUtil.beanToMap(operateLogPageVo, true, true);
        param.put("start", start);
        PageUtils pageUtils = this.logService.searchOperateLogsByPage(param);
        return R.ok()
                .put("result", pageUtils);
    }

    /**
     * 删除登录日志
     * @param ids 待删除登录日志id列表
     * @return 删除所影响的行数
     */
    @PreAuthorize("hasAuthority('ADMIN_LOG_LOGIN_DELETE')")
    @OperateLog(optType = "删除登录日志")
    @ApiOperation(value = "删除登录日志")
    @DeleteMapping("/admin/logs/login")
    public R deleteLoginLogs(@RequestParam List<Long> ids) {
        int role = this.logService.deleteLoginLogs(ids);
        return R.ok()
                .put("role", role);
    }

    /**
     * 删除操作日志
     * @param ids 待删除操作日志id列表
     * @return 删除所影响的行数
     */
    @PreAuthorize("hasAuthority('ADMIN_LOG_OPERATE_DELETE')")
    @OperateLog(optType = "删除操作日志")
    @ApiOperation(value = "删除操作日志")
    @DeleteMapping("/admin/logs/operate")
    public R deleteOperateLogs(@RequestParam List<Long> ids) {
        int role = this.logService.deleteOperateLogs(ids);
        return R.ok()
                .put("role", role);
    }
}
