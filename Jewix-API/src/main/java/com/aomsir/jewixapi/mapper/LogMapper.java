package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.dto.LoginLogDTO;
import com.aomsir.jewixapi.pojo.dto.OperateLogDTO;
import com.aomsir.jewixapi.pojo.entity.LoginLog;
import com.aomsir.jewixapi.pojo.entity.OperatedLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface LogMapper {
    void insertLoginLog(@Param("loginLog") LoginLog loginLog);

    int insertOperateLog(@Param("param") OperatedLog operatedLog);

    int queryLoginLogCounts(@Param("param") Map<String, Object> param);

    List<LoginLogDTO> queryLoginLogsByPage(@Param("param") Map<String, Object> param);

    int queryOperateLogCounts(@Param("param") Map<String, Object> param);

    List<OperateLogDTO> queryOperateLogsByPage(@Param("param") Map<String, Object> param);

    int deleteLoginLogs(@Param("ids") List<Long> ids);

    int deleteOperateLogs(@Param("ids") List<Long> ids);
}
