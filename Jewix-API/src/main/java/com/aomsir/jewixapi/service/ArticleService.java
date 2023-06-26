package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.dto.ArticleDetailDTO;
import com.aomsir.jewixapi.pojo.dto.ArticleRandomDTO;
import com.aomsir.jewixapi.pojo.vo.ArticleAddVo;
import com.aomsir.jewixapi.pojo.vo.ArticleUpdateVo;
import com.aomsir.jewixapi.util.PageUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/6/26
 * @Description: 文章业务接口
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface ArticleService {

    /**
     * 前台分页查询文章列表
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchFrontArticleListByPage(Map<String, Object> param);

    /**
     * 后台分页查询文章列表
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchBackendArticleListByPage(Map<String, Object> param);

    /**
     * 新增文章
     * @param articleAddVo 封装对象
     * @return 新增文章所影响的行数
     */
    int addArticle(ArticleAddVo articleAddVo);

    /**
     * 更新文章
     * @param articleUpdateVo 封装对象
     * @return 更新对象所影响的行数
     */
    int updateArticle(ArticleUpdateVo articleUpdateVo);


    /**
     * 根据uuid查询文章详情
     * @param uuid 文章uuid
     * @param request request请求对象
     * @return 文章详情
     */
    ArticleDetailDTO queryArticleByUuid(String uuid, HttpServletRequest request);

    /**
     * 根据uuid查询文章详情
     * @param uuid 文章uuid
     * @return 文章详情
     */
    ArticleDetailDTO queryArticleByUuid(String uuid);


    /**
     * 逻辑删除文章
     * @param ids 文章id列表
     * @return 删除所影响的行数
     */
    int deleteArticleByArchive(List<Long> ids);


    /**
     * 物理删除文章
     * @param ids 文章id列表
     * @return 删除所影响的行数
     */
    int deleteArticleByPhysics(List<Long> ids);


    /**
     * 随机查询文章列表
     * @return 随机文章列表
     */
    List<ArticleRandomDTO> searchRandomArticle();


    /**
     * 归档查询文章列表
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchArticlesByArchive(Map<String, Object> param);
}
