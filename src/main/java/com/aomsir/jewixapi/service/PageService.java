package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.entity.Page;
import com.aomsir.jewixapi.pojo.vo.PageAddVo;
import com.aomsir.jewixapi.pojo.vo.PageDeleteVo;
import com.aomsir.jewixapi.pojo.vo.PageUpdateVo;

import java.util.List;

public interface PageService {
    List<Page> getPageList();

    Page getPageByOmit(Integer omit);

    int addPage(PageAddVo page);

    int deletePage(PageDeleteVo page);

    int updatePage(PageUpdateVo page);
}
