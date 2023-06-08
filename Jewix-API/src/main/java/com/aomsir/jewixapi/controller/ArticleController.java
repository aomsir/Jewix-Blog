package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.pojo.dto.ArticleDetailDTO;
import com.aomsir.jewixapi.pojo.dto.ArticleRandomDTO;
import com.aomsir.jewixapi.pojo.vo.ArticleAddVo;
import com.aomsir.jewixapi.pojo.vo.ArticleBackendPageVo;
import com.aomsir.jewixapi.pojo.vo.ArticleFrontPageVo;
import com.aomsir.jewixapi.pojo.vo.ArticleUpdateVo;
import com.aomsir.jewixapi.service.ArticleService;
import com.aomsir.jewixapi.util.PageUtils;
import com.aomsir.jewixapi.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/3/7
 * @Description: 文章控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Api(tags = "文章控制器")
@RestController
public class ArticleController {

    @Resource
    private ArticleService articleService;


    /**
     * 后台无限制获取文章列表
     * @param articleBackendPageVo 后台无限制获取文章列表VO对象
     * @return 文章分页列表
     */
    @PreAuthorize("hasAuthority('ADMIN_ARTICLE_LIST')")
    @ApiOperation(value = "后台无限制分页获取文章列表", notes = "后台无限制分页获取文章列表")
    @GetMapping("/admin/articles")
    public R getBackendArticleByPage(ArticleBackendPageVo articleBackendPageVo) {
        Map<String, Object> param = BeanUtil.beanToMap(articleBackendPageVo);
        int page = (Integer) param.get("page");
        int length = (Integer) param.get("length");
        int start = (page - 1) * length;
        param.put("start",start);

        PageUtils pageUtils = this.articleService.searchBackendArticleListByPage(param);
        return R.ok()
                .put("result", pageUtils);
    }


    /**
     * 前台获取文章分页列表
     * @param articleFrontPageVo 前台分页获取文章预览VO对象
     * @return 预览文章分页列表
     */
    @ApiOperation(value = "前台获取文章分页列表", notes = "前台获取文章分页列表")
    @GetMapping("/articles")
    public R getFrontArticleByPage(ArticleFrontPageVo articleFrontPageVo) {
        Map<String, Object> param = BeanUtil.beanToMap(articleFrontPageVo);
        int page = (Integer) param.get("page");
        int length = (Integer) param.get("length");
        int start = (page - 1) * length;
        param.put("start",start);

        PageUtils pageUtils = this.articleService.searchFrontArticleListByPage(param);
        return R.ok()
                .put("result", pageUtils);
    }


    /**
     * 添加文章
     * @param articleAddVo 添加文章VO对象
     * @return 添加文章所影响的行数
     */
    @PreAuthorize("hasAuthority('ADMIN_ARTICLE_ADD')")
    @ApiOperation(value = "添加文章", notes = "添加文章")
    @PostMapping("/admin/articles")
    public R addArticle(@RequestBody @Validated ArticleAddVo articleAddVo) {
        int role = this.articleService.addArticle(articleAddVo);
        return R.ok()
                .put("role",role);
    }


    /**
     * 修改文章
     * @param articleUpdateVo 更新文章VO对象
     * @return 更新文章所影响的行数
     */
    @PreAuthorize("hasAuthority('ADMIN_ARTICLE_UPDATE')")
    @ApiOperation(value = "修改文章", notes = "修改文章")
    @PutMapping("/admin/articles")
    public R updateArticle(@RequestBody @Validated ArticleUpdateVo articleUpdateVo) {
        int role = this.articleService.updateArticle(articleUpdateVo);
        return R.ok()
                .put("role", role);
    }


    /**
     * 文章详情
     * @param uuid 文章uuid
     * @return 文章详情
     */
    @ApiOperation(value = "文章详情", notes = "文章详情")
    @GetMapping("/articles/{uuid}")
    public R getArticleByUuid(@PathVariable("uuid") String uuid,
                              HttpServletRequest httpServletRequest) {
        ArticleDetailDTO articleDetailDTO = this.articleService.queryArticleByUuid(uuid,httpServletRequest);
        return R.ok()
                .put("result",articleDetailDTO);
    }


    /**
     *
     * @param ids 待删除的文章id列表
     * @return 删除所影响的行数
     */
    @PreAuthorize("hasAuthority('ADMIN_ARTICLE_DELETE_ARCHIVE')")
    @ApiOperation(value = "理论删除文章", notes = "理论删除文章")
    @DeleteMapping("/admin/articles/archive")
    public R deleteArticleByArchive(@RequestParam List<Long> ids) {
        int role = this.articleService.deleteArticleByArchive(ids);
        return R.ok()
                .put("role",role);
    }

    /**
     *
     * @param ids 待删除的文章id列表
     * @return 删除所影响的行数
     */
    @PreAuthorize("hasAuthority('ADMIN_ARTICLE_DELETE_PHYSICS')")
    @ApiOperation(value = "物理删除文章", notes = "物理删除文章")
    @DeleteMapping("/admin/articles/physics")
    public R deleteArticleByPhysics(@RequestParam List<Long> ids) {
        int role = this.articleService.deleteArticleByPhysics(ids);
        return R.ok()
                .put("role",role);
    }


    /**
     * 获取推荐文章列表
     * @return 预览数据
     */
    @ApiOperation(value = "获取推荐文章列表", notes = "获取推荐文章列表")
    @GetMapping("/articles/random")
    public R getArticlesRandom() {
        List<ArticleRandomDTO> list = this.articleService.searchRandomArticle();
        return R.ok()
                .put("result",list);
    }


    /**
     * 分页查询文章归档
     * @return 归档数据
     */
    @ApiOperation(value = "分页查询文章归档信息",notes = "分页查询文章归档信息")
    @GetMapping("/articles/archive")
    public R getArticlesByArchive(Integer page,
                                  Integer length) {
        Integer start = (page - 1) * length;
        Map<String, Object> param = new HashMap<String, Object>(){{
            put("page",page);
            put("length",length);
            put("start", start);
        }};

        PageUtils pageUtils = this.articleService.searchArticlesByArchive(param);
        return R.ok()
                .put("result",pageUtils);
    }
}
