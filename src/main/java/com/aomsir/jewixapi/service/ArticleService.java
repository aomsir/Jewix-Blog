package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.dto.ArticleDetailDTO;
import com.aomsir.jewixapi.pojo.vo.ArticleAddVo;
import com.aomsir.jewixapi.pojo.vo.ArticleUpdateVo;
import com.aomsir.jewixapi.utils.PageUtils;

import java.util.Map;

public interface ArticleService {
    PageUtils searchFrontArticleListByPage(Map<String, Object> param);

    PageUtils searchBackendArticleListByPage(Map<String, Object> param);

    int addArticle(ArticleAddVo articleAddVo);

    int updateArticle(ArticleUpdateVo articleUpdateVo);

    ArticleDetailDTO queryArticleByUuid(String uuid);
}
