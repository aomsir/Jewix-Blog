package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.dto.ArticleDetailDTO;
import com.aomsir.jewixapi.pojo.dto.ArticleRandomDTO;
import com.aomsir.jewixapi.pojo.vo.ArticleAddVo;
import com.aomsir.jewixapi.pojo.vo.ArticleUpdateVo;
import com.aomsir.jewixapi.util.PageUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface ArticleService {
    PageUtils searchFrontArticleListByPage(Map<String, Object> param);

    PageUtils searchBackendArticleListByPage(Map<String, Object> param);

    int addArticle(ArticleAddVo articleAddVo);

    int updateArticle(ArticleUpdateVo articleUpdateVo);

    ArticleDetailDTO queryArticleByUuid(String uuid, HttpServletRequest request);

    ArticleDetailDTO queryArticleByUuid(String uuid);

    int deleteArticleByArchive(List<Long> ids);

    int deleteArticleByPhysics(List<Long> ids);

    List<ArticleRandomDTO> searchRandomArticle();

    PageUtils searchArticlesByArchive(Map<String, Object> param);
}
