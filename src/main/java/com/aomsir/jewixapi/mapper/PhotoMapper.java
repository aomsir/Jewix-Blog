package com.aomsir.jewixapi.mapper;


import com.aomsir.jewixapi.pojo.entity.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface PhotoMapper {
    int insertPhoto(@Param("param") Map<String, Object> param);

    int queryPhotoAllCount();

    ArrayList<Photo> selectPhotoListByPage(@Param("param") Map<String, Object> param);

    int deletePhoto(@Param("param") Map<String, Object> param);
}
