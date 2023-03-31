package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.pojo.vo.*;
import com.aomsir.jewixapi.service.CommentService;
import com.aomsir.jewixapi.utils.PageUtils;
import com.aomsir.jewixapi.utils.R;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/3/13
 * @Description: 评论控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Api(tags = "评论控制器")
@RestController
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 后台无限制获取评论列表
     * @param commentBackendPageVo 后台无限制获取评论列表VO对象
     * @return 无限制评论列表
     */
    @ApiOperation(value = "后台无限制获取评论列表")
    @GetMapping("/admin/comments")
    public R getCommentsBackendListByPage(@Validated CommentBackendPageVo commentBackendPageVo) {
        Map<String, Object> param = BeanUtil.beanToMap(commentBackendPageVo);
        int page = (Integer) param.get("page");
        int length = (Integer) param.get("length");
        int start = (page - 1) * length;
        param.put("start",start);

        PageUtils pageUtils = this.commentService.searchBackendCommentListByPage(param);
        return R.ok()
                .put("result", pageUtils);
    }

    /**
     * 前台根据文章id与类型获取评论列表
     * @param commentFrontVo 前台获取评论VO对象
     * @return 评论列表
     */
    @ApiOperation(value = "前台根据文章id与类型获取评论列表")
    @GetMapping("/comments")
    public R getCommentsById(@Validated CommentFrontVo commentFrontVo) {
        Map<String, Object> param = BeanUtil.beanToMap(commentFrontVo);
        int page = (Integer) param.get("page");
        int length = (Integer) param.get("length");
        int start = (page - 1) * length;
        param.put("start",start);

        PageUtils pageUtils = this.commentService.searchFrontCommentListByPage(param);
        return R.ok()
                .put("result", pageUtils);
    }


    /**
     * 添加评论
     * @param commentAddVo 添加评论VO对象
     * @param request request对象
     * @return 添加评论所影响的行数
     */
    @ApiOperation(value = "添加评论")
    @PostMapping("/admin/comments")
    public R addComment(@RequestBody @Validated CommentAddVo commentAddVo,
                        HttpServletRequest request) throws JsonProcessingException {
        int role = this.commentService.addComment(commentAddVo,request);
        return R.ok()
                .put("role",role);
    }


    /**
     * 后台修改评论相关内容
     * @param commentUpdateVo 修改评论VO对象
     * @return 更新评论所影响的行数
     */
    @ApiOperation(value = "后台修改评论相关内容")
    @PutMapping("/admin/comments")
    public R updateComment(@RequestBody @Validated CommentUpdateVo commentUpdateVo) {
        // TODO：内容状态等等
        int role = this.commentService.updateComment(commentUpdateVo);
        return R.ok()
                .put("role",role);
    }

    /**
     * 根据ids删除文章评论
     * @param commentDeleteVo 删除评论VO对象
     * @return 删除评论所影响的行数
     */
    @ApiOperation(value = "根据id列表删除文章评论")
    @DeleteMapping("/admin/comments")
    public R deleteComment(@RequestBody @Validated CommentDeleteVo commentDeleteVo) {

        int role = this.commentService.deleteComment(commentDeleteVo);
        return R.ok()
                .put("role",role);
    }

    /**
     * 修改评论状态
     * @param commentUpdateStatusVo 修改评论状态VO对象
     * @return 修改评论状态所影响的行数
     */
    @ApiOperation(value = "修改评论状态")
    @PutMapping("/admin/comments/status")
    public R updateCommentStatus(@RequestBody @Validated CommentUpdateStatusVo commentUpdateStatusVo) {
        int role = this.commentService.updateCommentStatus(commentUpdateStatusVo);
        return R.ok()
                .put("role",role);
    }

}
