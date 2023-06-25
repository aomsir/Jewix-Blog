package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.dto.LoginLogDTO;
import com.aomsir.jewixapi.pojo.dto.OperateLogDTO;
import com.aomsir.jewixapi.pojo.entity.LoginLog;
import com.aomsir.jewixapi.pojo.entity.OperatedLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/4/15
 * @Description: 日志Mapper
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Mapper
public interface LogMapper {
    /**
     * 新增登录日志
     * @param loginLog 登录日志
     */
    void insertLoginLog(@Param("loginLog") LoginLog loginLog);

    /**
     * 新增操作日志
     * @param operatedLog 操作日志
     * @return 新增所影响的行数
     */
    int insertOperateLog(@Param("param") OperatedLog operatedLog);

    /**
     * 查询登录日志数量
     * @param param 参数列表
     * @return 日志数量
     */
    int queryLoginLogCounts(@Param("param") Map<String, Object> param);

    /**
     * 分页查询登录日志
     * @param param 参数列表
     * @return 日志列表
     */
    List<LoginLogDTO> queryLoginLogsByPage(@Param("param") Map<String, Object> param);

    /**
     * 查询操作日志数量
     * @param param 参数列表
     * @return 操作日志数量
     */
    int queryOperateLogCounts(@Param("param") Map<String, Object> param);

    /**
     * 分页查询操作日志
     * @param param 参数列表
     * @return 日志列表
     */
    List<OperateLogDTO> queryOperateLogsByPage(@Param("param") Map<String, Object> param);

    /**
     * 删除登录日志
     * @param ids 日志id列表
     * @return 删除所影响的行数
     */
    int deleteLoginLogs(@Param("ids") List<Long> ids);

    /**
     * 删除操作日志
     * @param ids 日志id列表
     * @return 删除所影响的行数
     */
    int deleteOperateLogs(@Param("ids") List<Long> ids);
}
