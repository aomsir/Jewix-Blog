package com.aomsir.jewixapi.service;


import com.aomsir.jewixapi.pojo.entity.Tag;
import com.aomsir.jewixapi.pojo.vo.TagUpdateVo;
import com.aomsir.jewixapi.util.PageUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/6/26
 * @Description: 标签业务接口
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface TagService {

    /**
     * 分页查询标签列表
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchTagByPage(Map<String, Object> param);


    /**
     * 新增标签
     * @param tagName 标签名
     * @return 新增标签的主键值
     */
    int addTagByName(String tagName);

    /**
     * 根据id更新标签
     * @param updateVo 封装对象
     * @return 更新所影响的行数
     */
    int updateTagById(TagUpdateVo updateVo);

    /**
     * 根据id查询标签详情
     * @param tagId 标签id
     * @return 标签详情
     */
    Tag searchTagById(Long tagId);


    /**
     * 根据标签名分页查询文章列表
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchArticleListByTagName(Map<String, Object> param);


    /**
     * 删除标签
     * @param tagIds 标签id列表
     * @return 删除所影响的行数
     */
    int deleteTags(List<Long> tagIds);
}
