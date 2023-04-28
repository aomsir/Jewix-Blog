package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.dto.ArticleArchiveInfoDTO;
import com.aomsir.jewixapi.pojo.dto.WebInfoDTO;

public interface CommonService {
    WebInfoDTO queryWebInfo();

    ArticleArchiveInfoDTO queryArticleArchiveInfo();

}
