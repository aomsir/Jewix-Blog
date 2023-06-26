package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.dto.ArticleArchiveInfoDTO;
import com.aomsir.jewixapi.pojo.dto.WebInfoDTO;


/**
 * @Author: Aomsir
 * @Date: 2023/6/26
 * @Description: 通用业务接口
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface CommonService {

    /**
     * 查询网站信息
     * @return 网站信息
     */
    WebInfoDTO searchWebInfo();


    /**
     * 查询文章归档信息
     * @return 归档信息
     */
    ArticleArchiveInfoDTO searchArticleArchiveInfo();

}
