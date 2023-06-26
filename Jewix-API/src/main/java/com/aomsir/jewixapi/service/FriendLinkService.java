package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.entity.FriendLink;
import com.aomsir.jewixapi.util.PageUtils;

import java.util.List;
import java.util.Map;


/**
 * @Author: Aomsir
 * @Date: 2023/6/26
 * @Description: 友链业务接口
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface FriendLinkService {

    /**
     * 分页查询友链列表
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchFriendLinkListByPage(Map<String, Object> param);


    /**
     * 新增友链
     * @param param 参数列表
     * @return 新增友链所影响的行数
     */
    int addFriendLink(Map<String, Object> param);


    /**
     * 更新友链
     * @param param 参数列表
     * @return 更新所影响的行数
     */
    int updateFriendLink(Map<String, Object> param);


    /**
     * 根据id查询友链详情
     * @param id 友链id
     * @return 友链详情
     */
    FriendLink searchFriendLinkInfoById(Integer id);


    /**
     * 删除友链
     * @param ids id列表
     * @return 删除所影响的行数
     */
    int deleteFriendLinks(List<Integer> ids);
}
