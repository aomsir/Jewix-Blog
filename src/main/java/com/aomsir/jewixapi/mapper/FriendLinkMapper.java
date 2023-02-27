package com.aomsir.jewixapi.mapper;


import com.aomsir.jewixapi.pojo.entity.FriendLink;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;

@Mapper
public interface FriendLinkMapper {
    int queryFriendLinkCount(@Param("location") Integer location);

    ArrayList<FriendLink> queryFriendLinkByPage(@Param("param") Map<String, Object> param);

    FriendLink queryFriendLinkByTitle(@Param("title") String title);

    int insertFriendLink(@Param("param") Map<String, Object> param);

    FriendLink queryFriendLinkById(@Param("id") Integer id);

    int updateFriendLink(@Param("param") Map<String, Object> param);
}
