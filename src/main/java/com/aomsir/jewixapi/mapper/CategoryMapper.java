package com.aomsir.jewixapi.mapper;


import com.aomsir.jewixapi.pojo.dto.ArticlePreviewDTO;
import com.aomsir.jewixapi.pojo.dto.CategoryListDTO;
import com.aomsir.jewixapi.pojo.entity.Category;
import com.aomsir.jewixapi.pojo.vo.CategoryUpdateVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface CategoryMapper {
    int queryCategoryCountByParentId(@Param("parentId") Long parentId);

    ArrayList<Category> queryCategoryListPageByParentId(@Param("param") Map<String, Object> param);

    Category queryCategoryByParentId(@Param("parentId") Long parentId);

    int insertCategory(@Param("category") Category category_2);

    Category queryCategoryByName(@Param("categoryName") String categoryName);

    List<ArticlePreviewDTO> queryArticleListPageByCategoryName(@Param("param") Map<String, Object> param);

    Boolean queryIdsExists(@Param("list") List<Long> categoryIds);

    List<CategoryListDTO> queryCategoryList();

    Integer queryArticleCommentCountsById(@Param("articleId") Long id);

    List<Category> queryCategoryListByParentId(@Param("parentId") Long parentId);

    Category queryCategoryByNameAndParentId(@Param("categoryName") String categoryName, @Param("parentId") Long parentId);

    Category queryCategoryId(@Param("id") Long id);

    int updateCategory(@Param("param") CategoryUpdateVo categoryUpdateVo);

    int queryCategoryOfArticleCounts(@Param("id") Long id);

    int deleteCategories(@Param("ids") List<Long> trueIds);

    List<ArticlePreviewDTO> queryArticleListPageByCategoryId(@Param("param") Map<String, Object> param);
}
