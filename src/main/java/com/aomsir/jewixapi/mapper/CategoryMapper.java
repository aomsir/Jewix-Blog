package com.aomsir.jewixapi.mapper;


import com.aomsir.jewixapi.pojo.dto.ArticlePreviewDTO;
import com.aomsir.jewixapi.pojo.dto.CategoryListDTO;
import com.aomsir.jewixapi.pojo.entity.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {
    int queryCategoryCountByParentId(@Param("parentId") Integer parentId);

    ArrayList<Category> queryCategoryListPageByParentId(@Param("param") Map<String, Object> param);

    Category queryCategoryByParentId(@Param("parentId") Integer parentId);

    int insertCategory(@Param("category") Category category_2);

    Category queryCategoryByName(@Param("categoryName") String categoryName);

    List<ArticlePreviewDTO> queryArticleListPageByCategoryName(@Param("param") Map<String, Object> param);

    Boolean queryIdsExists(@Param("list") List<Integer> categoryIds);

    List<CategoryListDTO> queryCategoryList();

    Integer queryArticleCountsById(@Param("articleId") Long id);
}
