package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.Resource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ResourceMapper {
    List<Resource> queryAuthoritiesByUserId(@Param("userId") Long id);
}
