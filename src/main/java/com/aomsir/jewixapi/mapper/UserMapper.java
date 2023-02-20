package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User queryUserByEmail(@Param("email") String email);
}
