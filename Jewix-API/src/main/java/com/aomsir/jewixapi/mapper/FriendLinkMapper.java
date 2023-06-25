package com.aomsir.jewixapi.mapper;


import com.aomsir.jewixapi.pojo.entity.FriendLink;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/4/15
 * @Description: 友情链接Mapper
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Mapper
public interface FriendLinkMapper {
    /**
     * 根据位置查询友链数量
     * @param location 位置
     * @return 友链数量
     */
    int queryFriendLinkCount(@Param("location") Integer location);

    /**
     * 分页查询友链列表
     * @param param 参数列表
     * @return 友链列表
     */
    ArrayList<FriendLink> queryFriendLinkByPage(@Param("param") Map<String, Object> param);

    /**
     * 根据标题查询友链详情
     * @param title 友链标题
     * @return 友链详情
     */
    FriendLink queryFriendLinkByTitle(@Param("title") String title);

    /**
     * 新增友链
     * @param param 参数列表
     * @return 新增友链所影响的行数
     */
    int insertFriendLink(@Param("param") Map<String, Object> param);

    /**
     * 根据id查询友链详情
     * @param id 友链id
     * @return 友链详情
     */
    FriendLink queryFriendLinkById(@Param("id") Integer id);

    /**
     * 更新友链
     * @param param 参数列表
     * @return 更新所影响的行数
     */
    int updateFriendLink(@Param("param") Map<String, Object> param);

    /**
     * 删除友链
     * @param ids 友链id列表
     * @return 删除所影响的行数
     */
    int deleteFriendLinks(@Param("ids") List<Integer> ids);

    /**
     * 根据链接查询友链详情
     * @param link 链接
     * @return 友链详情
     */
    FriendLink queryFriendLinkByLink(@Param("link") String link);
}
