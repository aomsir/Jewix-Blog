package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.dto.UserConfigDTO;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.pojo.vo.UserHaveVo;
import com.aomsir.jewixapi.pojo.vo.UserStatusVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/4/15
 * @Description: 用户Mapper
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Mapper
public interface UserMapper {
    /**
     * 根据邮箱查询用户详情
     * @param email 邮箱
     * @return 用户详情
     */
    User queryUserByEmail(@Param("email") String email);

    /**
     * 根据uuid查询用户详情
     * @param uuid 用户uuid
     * @return 用户详情
     */
    User queryUserByUuid(@Param("uuid") String uuid);

    /**
     * 查询站长用户详情
     * @return 站长用户详情
     */
    UserConfigDTO queryConfigUser();

    /**
     * 查询用户数量
     * @param status 状态
     * @param email 邮箱
     * @return 用户数量
     */
    Long queryUserCount(@Param("status") Integer status, @Param("email") String email);

    /**
     * 分页查询用户列表
     * @param param 参数列表
     * @return 用户列表
     */
    ArrayList<User> queryUserListByPage(@Param("param") Map<String, Object> param);

    /**
     * 根据昵称查询用户详情
     * @param nickname 用户昵称
     * @return 用户详情
     */
    User queryUserByNickname(@Param("nickname") String nickname);

    /**
     * 新增用户
     * @param param 参数列表
     * @return 新增所影响的行数
     */
    int insertUser(@Param("param") Map<String, Object> param);

    /**
     * 更新用户
     * @param param 参数列表
     * @return 更新所影响的行数
     */
    int updateUser(@Param("param") Map<String, Object> param);

    /**
     * 根据昵称或邮箱查询用户是否已存在
     * @param userHaveVo 封装对象
     * @return 是否存在
     */
    int queryUserByEmailOrNickname(@Param("param") UserHaveVo userHaveVo);

    /**
     * 更新用户状态
     * @param userStatusVo 封装对象
     * @return 更新所影响的行数
     */
    int updateUserStatus(@Param("param") UserStatusVo userStatusVo);

    /**
     * 根据id查询用户详情
     * @param userId 用户id
     * @return 用户详情
     */
    User queryUserById(@Param("userId") Long userId);

    /**
     * 逻辑删除用户
     * @param ids 用户id列表
     * @return 删除所影响的行数
     */
    int deleteUserByArchive(@Param("ids") List<Long> ids);

    /**
     * 物理删除用户
     * @param ids 用户id列表
     * @return 删除所影响的行数
     */
    int deleteUserByPhysics(@Param("ids") List<Long> ids);
}
