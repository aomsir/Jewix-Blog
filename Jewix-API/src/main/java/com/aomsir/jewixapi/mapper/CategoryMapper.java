package com.aomsir.jewixapi.mapper;


import com.aomsir.jewixapi.pojo.dto.ArticlePreviewDTO;
import com.aomsir.jewixapi.pojo.entity.Category;
import com.aomsir.jewixapi.pojo.vo.CategoryUpdateVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/4/15
 * @Description: 分类Mapper
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Mapper
public interface CategoryMapper {
    /**
     * 根据父分类id查询分类数量
     * @param parentId 父分类id
     * @return 分类数量
     */
    int queryCategoryCountByParentId(@Param("parentId") Long parentId);

    /**
     * 根据父分类id分页查询分类列表
     * @param param 参数列表
     * @return 分类列表
     */
    ArrayList<Category> queryCategoryListPageByParentId(@Param("param") Map<String, Object> param);

    /**
     * 根据父分类id查询分类
     * @param parentId 父分类id
     * @return 分类详情
     */
    Category queryCategoryByParentId(@Param("parentId") Long parentId);

    /**
     * 新增分类
     * @param category 分类详情
     * @return 插入所影响的行数
     */
    int insertCategory(@Param("category") Category category);

    /**
     * 根据分类名查询分类详情
     * @param categoryName 分类名
     * @return 分类详情
     */
    Category queryCategoryByName(@Param("categoryName") String categoryName);

    /**
     * 根据分类名分页关联查询文章列表
     * @param param 参数列表
     * @return 文章详情列表
     */
    List<ArticlePreviewDTO> queryArticleListPageByCategoryName(@Param("param") Map<String, Object> param);

    /**
     * 查询分类id是否已经存在
     * @param categoryIds 分类id
     * @return 是否存在
     */
    Boolean queryIdsExists(@Param("list") List<Long> categoryIds);


    /**
     * 根据文章id查询文章评论数量
     * @param id 文章id
     * @param status 评论状态
     * @return 评论数量
     */
    Integer queryArticleCommentCountsById(@Param("articleId") Long id,
                                          @Param("status") Integer status);

    /**
     * 根据父分类id查询分类列表
     * @param parentId 父分类id
     * @return 分类列表
     */
    List<Category> queryCategoryListByParentId(@Param("parentId") Long parentId);

    /**
     * 根据分类名与父分类id查询分类详情
     * @param categoryName 分类名
     * @param parentId 父分类id
     * @return 分类信息
     */
    Category queryCategoryByNameAndParentId(@Param("categoryName") String categoryName, @Param("parentId") Long parentId);

    /**
     * 根据分类id查询分类详情
     * @param id 分类id
     * @return 分类详情
     */
    Category queryCategoryId(@Param("id") Long id);

    /**
     * 更新分类
     * @param categoryUpdateVo 更新分类信息
     * @return 更新分类所影响的行数
     */
    int updateCategory(@Param("param") CategoryUpdateVo categoryUpdateVo);

    /**
     * 根据分类id查询所引用的文章数量
     * @param id 分类id
     * @return 文章数量
     */
    int queryCategoryOfArticleCounts(@Param("id") Long id);

    /**
     * 删除分类
     * @param trueIds 分类id列表
     * @return 所影响的行数
     */
    int deleteCategories(@Param("ids") List<Long> trueIds);

    /**
     * 根据分类id分页查询文章预览列表
     * @param param 参数列表
     * @return 文章预览列表
     */
    List<ArticlePreviewDTO> queryArticleListPageByCategoryId(@Param("param") Map<String, Object> param);

    /**
     * 查询分类数量
     * @return 分类数量
     */
    Integer queryCategoryCount();
}
