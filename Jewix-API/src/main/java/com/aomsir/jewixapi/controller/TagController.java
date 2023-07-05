package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.annotation.OperateLog;
import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.pojo.entity.Tag;
import com.aomsir.jewixapi.pojo.vo.ArticleTagVo;
import com.aomsir.jewixapi.pojo.vo.TagAddVo;
import com.aomsir.jewixapi.pojo.vo.TagPageVo;
import com.aomsir.jewixapi.pojo.vo.TagUpdateVo;
import com.aomsir.jewixapi.service.TagService;
import com.aomsir.jewixapi.util.PageUtils;
import com.aomsir.jewixapi.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description: 标签控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Api(tags = "标签控制器")
@RestController
public class TagController {

    @Resource
    private TagService tagService;

    /**
     * 分页获取标签列表
     * @param tagPageVo 分页获取标签VO对象
     * @return 分页标签列表
     */
    @ApiOperation(value = "分页获取标签列表")
    @GetMapping("/tags")
    public R getTagListByPage(@Validated TagPageVo tagPageVo) {
        Map<String, Object> param = BeanUtil.beanToMap(tagPageVo);
        int page = tagPageVo.getPage();
        int length = tagPageVo.getLength();
        int start = (page - 1) * length;
        param.put("start", start);
        PageUtils pageUtils = this.tagService.searchTagByPage(param);
        return R.ok()
                .put("result", pageUtils);
    }

    /**
     * 添加新标签
     * @param tagAddVo 添加标签VO对象
     * @return 添加标签所影响的行数
     */
    @OperateLog(optType = "添加标签")
    @PreAuthorize("hasAuthority('ADMIN_TAG_ADD')")
    @ApiOperation(value = "添加新标签")
    @PostMapping("/admin/tags")
    public R addTag(@RequestBody @Validated TagAddVo tagAddVo) {
         Long role = this.tagService.addTagByName(tagAddVo.getTagName());
         return R.ok()
                 .put("result",role);
    }

    /**
     * 根据id修改标签名
     * @param updateVo 更新标签VO对象
     * @return 更新标签所影响的行数
     */
    @OperateLog(optType = "修改标签")
    @PreAuthorize("hasAuthority('ADMIN_TAG_UPDATE')")
    @ApiOperation(value = "根据id修改标签名")
    @PutMapping("/admin/tags")
    public R updateTag(@RequestBody @Validated TagUpdateVo updateVo) {
        int role = this.tagService.updateTagById(updateVo);
        return R.ok()
                .put("role", role);
    }

    /**
     * 根据id查询标签
     * @param tagId 标签id
     * @return 标签
     */
    @PreAuthorize("hasAuthority('ADMIN_TAG_DETAIL')")
    @ApiOperation(value = "根据id查询标签")
    @GetMapping("/admin/tags/{tagId}")
    public R getTagById(@PathVariable("tagId") Long tagId) {
        if (tagId < 1) {
            throw new CustomerException("标签不存在");
        }

        Tag tag = this.tagService.searchTagById(tagId);
        return R.ok()
                .put("tag", tag);
    }


    @OperateLog(optType = "删除标签")
    @PreAuthorize("hasAuthority('ADMIN_TAG_DELETE')")
    @ApiOperation(value = "删除标签")
    @DeleteMapping("/admin/tags")
    public R deleteTags(@RequestParam List<Long> tagIds) {
        int role = this.tagService.deleteTags(tagIds);
        return R.ok()
                .put("role", role);
    }


    /**
     * 根据标签名分页查询预览文章列表
     * @param articleTagVo 文章-标签VO对象
     * @return 根据标签获取的文章分页列表
     */
    @ApiOperation(value = "根据标签名分页查询预览文章列表")
    @GetMapping("/tags/articles")
    public R getArticlesByTagName(@Validated ArticleTagVo articleTagVo) {
        Map<String, Object> param = BeanUtil.beanToMap(articleTagVo);
        int page = articleTagVo.getPage();
        int length = articleTagVo.getLength();
        int start = (page - 1) * length;
        param.put("start", start);

        PageUtils result = this.tagService.searchArticleListByTagName(param);
        return R.ok()
                .put("result", result);
    }
}
