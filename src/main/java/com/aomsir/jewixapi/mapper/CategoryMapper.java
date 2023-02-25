package com.aomsir.jewixapi.mapper;


import com.aomsir.jewixapi.pojo.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface CategoryMapper {
    int queryCategoryCountByParentId(@Param("parentId") Integer parentId);

    ArrayList<Category> queryCategoryListPageByParentId(@Param("param") Map<String, Object> param);
}
