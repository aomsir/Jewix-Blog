package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.dto.CurrentUserDTO;
import com.aomsir.jewixapi.pojo.dto.UserConfigDTO;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.pojo.vo.*;
import com.aomsir.jewixapi.util.PageUtils;

import java.util.List;
import java.util.Map;


/**
 * @Author: Aomsir
 * @Date: 2023/6/26
 * @Description: 用户业务接口
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface UserService {

    /**
     * 根据uuid查询用户详情
     * @param uuid 用户uuid
     * @return 用户详情
     */
    User searchUserByUuid(String uuid);

    /**
     * 查询站长信息
     * @return 站长信息封装对象
     */
    UserConfigDTO searchConfigUser();

    /**
     * 分页查询用户列表
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchUserByPage(Map<String, Object> param);

    /**
     * 新增用户
     * @param userAddVo 封装对象
     * @return 新增用户的主键值
     */
    int addUser(UserAddVo userAddVo);


    /**
     * 更新用户
     * @param userUpdateVo 封装对象
     * @return 更新所影响的行数
     */
    int updateUser(UserUpdateVo userUpdateVo);

    /**
     * 查询用户是否存在
     * @param userHaveVo 封装对象
     * @return 是否存在标志
     */
    int hasUser(UserHaveVo userHaveVo);


    /**
     * 更新用户状态
     * @param userStatusVo 封装对象
     * @return 更新所影响的行数
     */
    int updateStatus(UserStatusVo userStatusVo);


    /**
     * 查询当前登录用户
     * @return 当前登录用户
     */
    CurrentUserDTO searchCurrentUser();


    /**
     * 逻辑删除用户
     * @param ids 用户id列表
     * @return 删除所影响的行数
     */
    int deleteUserByArchive(List<Long> ids);


    /**
     * 物理删除用户
     * @param ids 用户id列表
     * @return 删除所影响的行数
     */
    int deleteUserByPhysics(List<Long> ids);


    /**
     * 用户自更新数据
     * @param userUpdateVo 封装对象
     * @return 更新所影响的行数
     */
    int updateUserByMyself(UserUpdateVo userUpdateVo);
}
