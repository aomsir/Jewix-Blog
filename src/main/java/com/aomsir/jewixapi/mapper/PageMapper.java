package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PageMapper {
    List<Page> queryPageList();

    Page queryPageByOmit(@Param("omit") Integer omit);

    int insertPage(@Param("param") Map<String, Object> param);

    int updatePage(@Param("param") Map<String, Object> param);

    Page queryPageById(@Param("id") Integer id);

    void deletePageByIdAndOmit(@Param("id") Integer id, @Param("omit") Integer omit);
}
