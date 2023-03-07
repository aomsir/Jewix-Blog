package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.entity.WebConfig;
import com.aomsir.jewixapi.pojo.vo.InfoWebConfigAddVo;
import com.aomsir.jewixapi.pojo.vo.InfoWebConfigUpdateVo;

public interface WebConfigService {
    int addWebConfig(InfoWebConfigAddVo infoWebConfigAddVo);

    int updateWebConfig(InfoWebConfigUpdateVo infoWebConfigUpdateVo);

    WebConfig searchInfoAllByType(Integer type);
}
