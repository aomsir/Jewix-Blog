package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.dto.PageListDTO;
import com.aomsir.jewixapi.pojo.entity.Page;
import com.aomsir.jewixapi.pojo.vo.PageAddVo;
import com.aomsir.jewixapi.pojo.vo.PageUpdateVo;

import java.util.List;


/**
 * @Author: Aomsir
 * @Date: 2023/6/26
 * @Description: 页面业务接口
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface PageService {

    /**
     * 分页查询页面数据
     * @return 页面列表数据
     */
    List<PageListDTO> searchPageList();


    /**
     * 根据uuid查询页面详情
     * @param uuid 页面uuid
     * @return 页面详情
     */
    Page searchPageByUuid(String uuid);


    /**
     * 新增页面
     * @param pageAddVo 封装对象
     * @return 新增所影响的行数
     */
    int addPage(PageAddVo pageAddVo);


    /**
     * 更新页面
     * @param pageUpdateVo 封装对象
     * @return 更新所影响的行数
     */
    int updatePage(PageUpdateVo pageUpdateVo);


    /**
     * 删除页面
     * @param uuid 页面uuid
     * @return 删除所影响的行数
     */
    int deletePage(String uuid);
}
