package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface MenuMapper {
    int queryMenuCountsOfParentByPage(@Param("param") Map<String, Object> param);

    ArrayList<Menu> queryMenuOfParentByPage(@Param("param") Map<String, Object> param);

    List<Menu> queryMenusByParentId(@Param("id") Integer id);
}
