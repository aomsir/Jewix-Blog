package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.pojo.dto.ArticleDetailDTO;
import com.aomsir.jewixapi.pojo.vo.ArticleAddVo;
import com.aomsir.jewixapi.pojo.vo.ArticleBackendPageVo;
import com.aomsir.jewixapi.pojo.vo.ArticleFrontPageVo;
import com.aomsir.jewixapi.pojo.vo.ArticleUpdateVo;
import com.aomsir.jewixapi.service.ArticleService;
import com.aomsir.jewixapi.utils.PageUtils;
import com.aomsir.jewixapi.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Delete;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    @ApiOperation(value = "后台无限制分页获取文章列表", notes = "后台无限制分页获取文章列表")
    @GetMapping("/admin/articles")
    public R getBackendArticleByPage(@Validated ArticleBackendPageVo articleBackendPageVo) {
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
    public R getFrontArticleByPage(@Valid ArticleFrontPageVo articleFrontPageVo) {
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
    @ApiOperation(value = "物理删除文章", notes = "物理删除文章")
    @DeleteMapping("/admin/articles/physics")
    public R deleteArticleByPhysics(@RequestParam List<Long> ids) {
        int role = this.articleService.deleteArticleByPhysics(ids);
        return R.ok()
                .put("role",role);
    }
}
