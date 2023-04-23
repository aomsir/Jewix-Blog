package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.LoginLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LogMapper {
    void insertLoginLog(@Param("loginLog") LoginLog loginLog);
}
