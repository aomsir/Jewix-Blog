package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.dto.PageListDTO;
import com.aomsir.jewixapi.pojo.entity.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface PageMapper {
    List<PageListDTO> queryPageList();

    List<Long> queryPageUserIds();

    Page queryPageByUuid(@Param("uuid") String uuid);

    Page queryPageByTitle(@Param("title") String title);

    int insertPage(@Param("param") Page newPage);

    Page queryPageByType(@Param("type") Integer type);

    int updatePage(@Param("param") Page page2);

    int deletePage(@Param("uuid") String uuid);
}
