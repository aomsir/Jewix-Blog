package com.aomsir.jewixapi.mapper;


import com.aomsir.jewixapi.pojo.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface CategoryMapper {
    int queryCategoryParentCount();

    ArrayList<Category> queryCategoryParentListByPage(@Param("param") Map<String, Object> param);
}
