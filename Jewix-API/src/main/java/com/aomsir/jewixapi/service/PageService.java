package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.dto.PageListDTO;
import com.aomsir.jewixapi.pojo.entity.Page;
import com.aomsir.jewixapi.pojo.vo.PageAddVo;
import com.aomsir.jewixapi.pojo.vo.PageDeleteVo;
import com.aomsir.jewixapi.pojo.vo.PageUpdateVo;

import java.util.List;

public interface PageService {

    List<PageListDTO> searchPageList();

    Page searchPageByUuid(String uuid);

    int addPage(PageAddVo pageAddVo);

    int updatePage(PageUpdateVo pageUpdateVo);

    int deletePage(String uuid);
}
