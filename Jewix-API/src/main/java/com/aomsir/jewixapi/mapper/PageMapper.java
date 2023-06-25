package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.dto.PageListDTO;
import com.aomsir.jewixapi.pojo.entity.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Author: Aomsir
 * @Date: 2023/4/15
 * @Description: 页面Mapper
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Mapper
public interface PageMapper {
    /**
     * 查询页面列表
     * @return 页面列表
     */
    List<PageListDTO> queryPageList();

    /**
     * 根据用户id查询页面id列表
     * @return 页面id列表
     */
    List<Long> queryPageUserIds();

    /**
     * 根据uuid查询页面详情
     * @param uuid 页面uuid
     * @return 页面详情
     */
    Page queryPageByUuid(@Param("uuid") String uuid);

    /**
     * 根据标题查询页面详情
     * @param title 页面标题
     * @return 页面详情
     */
    Page queryPageByTitle(@Param("title") String title);

    /**
     * 新增页面
     * @param newPage 页面详情
     * @return 插入所影响的行数
     */
    int insertPage(@Param("param") Page newPage);

    /**
     * 根据类型查询页面详情
     * @param type 页面类型
     * @return 页面详情
     */
    Page queryPageByType(@Param("type") Integer type);

    /**
     * 更新页面
     * @param page2 页面数据
     * @return 更新页面所影响的行数
     */
    int updatePage(@Param("param") Page page2);

    /**
     * 删除页面
     * @param uuid 页面uuid
     * @return 删除页面所影响的行数
     */
    int deletePage(@Param("uuid") String uuid);

    /**
     * 根据路径查询页面详情
     * @param omit 页面路径
     * @return 页面详情
     */
    Page queryPageByOmit(@Param("omit") String omit);

    /**
     * 根据页面id查询用户id
     * @param targetId 页面id
     * @return 用户id
     */
    Long queryPageUserIdByPageId(@Param("targetId") Long targetId);

    /**
     * 根据id查询页面详情
     * @param targetId 页面id
     * @return 页面详情
     */
    Page queryPageById(@Param("id") Long targetId);
}
