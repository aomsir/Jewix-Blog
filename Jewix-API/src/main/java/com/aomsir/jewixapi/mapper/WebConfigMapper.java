package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.WebConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface WebConfigMapper {
    int insertWebConfig(@Param("param") Map<String, Object> param);

    WebConfig queryWebConfigInfo();

    WebConfig queryWebConfigById(@Param("id") Integer id);

    int updateWebConfig(@Param("param") Map<String, Object> param);
}
