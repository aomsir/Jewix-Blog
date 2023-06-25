package com.aomsir.jewixapi.mapper;

import com.aomsir.jewixapi.pojo.entity.WebConfig;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/4/15
 * @Description: 设置Mapper
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Mapper
public interface WebConfigMapper {
    /**
     * 新增网站设置
     * @param param 参数列表
     * @return 新增所影响的行数
     */
    int insertWebConfig(@Param("param") Map<String, Object> param);

    /**
     * 查询网站设置信息
     * @return 网站设置详情
     */
    WebConfig queryWebConfigInfo();

    /**
     * 根据id查询网站详情
     * @param id 设置id
     * @return 网站详情
     */
    WebConfig queryWebConfigById(@Param("id") Integer id);

    /**
     * 更新网站设置
     * @param param 参数列表
     * @return 更新所影响的行数
     */
    int updateWebConfig(@Param("param") Map<String, Object> param);
}
