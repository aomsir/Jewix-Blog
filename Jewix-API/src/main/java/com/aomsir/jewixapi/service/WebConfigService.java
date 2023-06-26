package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.entity.WebConfig;
import com.aomsir.jewixapi.pojo.vo.InfoWebConfigAddVo;
import com.aomsir.jewixapi.pojo.vo.InfoWebConfigUpdateVo;

/**
 * @Author: Aomsir
 * @Date: 2023/6/26
 * @Description: 设置业务接口
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface WebConfigService {

    /**
     * 新增网站设置
     * @param infoWebConfigAddVo 封装对象
     * @return 新增所影响的行数
     */
    int addWebConfig(InfoWebConfigAddVo infoWebConfigAddVo);


    /**
     * 更新网站设置
     * @param infoWebConfigUpdateVo 封装对象
     * @return 更新所影响的行数
     */
    int updateWebConfig(InfoWebConfigUpdateVo infoWebConfigUpdateVo);


    /**
     * 查询网站设置详情
     * @return 网站设置详情
     */
    WebConfig searchWebInfo();
}
