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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/3/7
 * @Description: 文章控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@RestController
public class ArticleController {

    @Resource
    private ArticleService articleService;


    /**
     * 后台无限制获取文章列表
     * @param articleBackendPageVo
     * @return
     */
    @GetMapping("/admin/articles/page")
    public R getBackendArticleByPage(@RequestBody @Validated ArticleBackendPageVo articleBackendPageVo) {
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
     * @param articleFrontPageVo
     * @return
     */
    @GetMapping("/articles/page")
    public R getFrontArticleByPage(@RequestBody @Validated ArticleFrontPageVo articleFrontPageVo) {
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
     * @param articleAddVo
     * @return
     */
    @PostMapping("/articles")
    public R addArticle(@RequestBody @Validated ArticleAddVo articleAddVo) {
        int role = this.articleService.addArticle(articleAddVo);
        return R.ok()
                .put("role",role);
    }


    /**
     * 修改文章
     * @param articleUpdateVo
     * @return
     */
    @PutMapping("/articles")
    public R updateArticle(@RequestBody @Validated ArticleUpdateVo articleUpdateVo) {
        int role = this.articleService.updateArticle(articleUpdateVo);
        return R.ok()
                .put("role", role);
    }


    /**
     * 文章详情
     * @param uuid
     * @return
     */
    @GetMapping("/articles/{uuid}")
    public R getArticleByUuid(@PathVariable("uuid") String uuid) {
        ArticleDetailDTO articleDetailDTO = this.articleService.queryArticleByUuid(uuid);
        return R.ok()
                .put("result",articleDetailDTO);
    }

}
