package com.aomsir.jewixapi.mapper;


import com.aomsir.jewixapi.pojo.entity.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/4/15
 * @Description: 相册Mapper
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Mapper
public interface PhotoMapper {
    /**
     * 新增相册
     * @param param 参数列表
     * @return 新增相册所影响的行数
     */
    int insertPhoto(@Param("param") Map<String, Object> param);

    /**
     * 查询相册总数
     * @return 相册数量
     */
    int queryPhotoAllCount();

    /**
     * 分页查询相册列表
     * @param param 参数列表
     * @return 相册列表
     */
    ArrayList<Photo> selectPhotoListByPage(@Param("param") Map<String, Object> param);

    /**
     * 删除相册
     * @param param 参数列表
     * @return 删除相册所影响的行数
     */
    int deletePhoto(@Param("param") Map<String, Object> param);

    /**
     * 根据类型查询相册数量
     * @param type 相册类型
     * @return 相册数量
     */
    int queryPhotoCountByType(@Param("type") Integer type);
}
