package com.aomsir.jewixapi.mapper;


import com.aomsir.jewixapi.pojo.dto.ArticlePreviewDTO;
import com.aomsir.jewixapi.pojo.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/4/15
 * @Description: 标签Mapper
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Mapper
public interface TagMapper {

    /**
     * 查询标签数量
     * @return 标签数量
     */
    Long queryTagCount();

    /**
     * 分页查询标签列表
     * @param param 参数列表
     * @return 标签数量
     */
    ArrayList<Tag> queryTagListByPage(@Param("param") Map<String, Object> param);

    /**
     * 根据名称查询标签详情
     * @param tagName 标签名称
     * @return 标签详情
     */
    Tag queryTagByName(@Param("tagName") String tagName);

    /**
     * 新增标签
     * @param tag 标签详情
     * @return 新增标签后的主键值
     */
    int insertTag(@Param("tag") Tag tag);

    /**
     * 根据id查询标签详情
     * @param id 标签id
     * @return 标签详情
     */
    Tag queryTagById(@Param("id") Long id);

    /**
     * 根据id更新标签
     * @param tag 标签信息
     * @return 更新后所影响的行数
     */
    int updateTagById(@Param("tag") Tag tag);

    /**
     * 根据标签名查询对应文章预览列表
     * @param param 参数列表
     * @return 文章预览列表
     */
    List<ArticlePreviewDTO> queryArticleListByTagName(@Param("param") Map<String, Object> param);

    /**
     * 查询标签是否已存在
     * @param tagIds 标签id
     * @return 存在与否
     */
    Boolean queryIdsExists(@Param("list") List<Long> tagIds);

    /**
     * 删除标签
     * @param tagIds 标签id列表
     * @return 删除所影响的行数
     */
    int deleteTags(@Param("tagIds") List<Long> tagIds);
}
