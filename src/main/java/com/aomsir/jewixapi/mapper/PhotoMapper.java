package com.aomsir.jewixapi.mapper;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface PhotoMapper {
    int insertPhoto(@Param("param") Map<String, Object> param);
}
