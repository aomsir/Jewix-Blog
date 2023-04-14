package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.dto.UserConfigDTO;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.pojo.vo.UserHaveVo;
import com.aomsir.jewixapi.pojo.vo.UserStatusVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface UserMapper {
    User queryUserByEmail(@Param("email") String email);

    User queryUserByUUID(@Param("uuid") String uuid);

    UserConfigDTO queryConfigUser();

    Long queryUserCount(@Param("status") Integer status, @Param("email") String email);

    ArrayList<User> queryUserListByPage(@Param("param") Map<String, Object> param);

    User queryUserByNickname(@Param("nickname") String nickname);

    int insertUser(@Param("param") Map<String, Object> param);

    int updateUser(@Param("param") Map<String, Object> param);

    int queryUserByEmailOrNickname(@Param("param") UserHaveVo userHaveVo);

    int updateUserStatus(@Param("param") UserStatusVo userStatusVo);

    User queryUserById(@Param("userId") Long userId);
}
