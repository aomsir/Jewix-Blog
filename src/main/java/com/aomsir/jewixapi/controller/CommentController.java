package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.pojo.vo.*;
import com.aomsir.jewixapi.service.CommentService;
import com.aomsir.jewixapi.utils.PageUtils;
import com.aomsir.jewixapi.utils.R;
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

@RestController
public class CommentController {

    @Resource
    private CommentService commentService;

    /**
     * 后台无限制获取评论列表
     * @param commentBackendPageVo
     * @return
     */
    @GetMapping("/admin/comments")
    public R getCommentsBackendListByPage(@RequestBody @Validated CommentBackendPageVo commentBackendPageVo) {
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
     * @param commentFrontVo
     * @return
     */
    @GetMapping("/comments")
    public R getCommentsById(@RequestBody @Validated CommentFrontVo commentFrontVo) {
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
     * @param commentAddVo
     * @param request
     * @return
     */
    @PostMapping("/comments")
    public R addComment(@RequestBody @Validated CommentAddVo commentAddVo,
                        HttpServletRequest request) {
        int role = this.commentService.addComment(commentAddVo,request);

        return R.ok()
                .put("role",role);
    }


    /**
     * 后台修改评论相关内容
     * @param commentUpdateVo
     * @return
     */
    @PutMapping("/admin/comments")
    public R updateComment(@RequestBody CommentUpdateVo commentUpdateVo) {
        // TODO：内容状态等等
        return null;
    }

    /**
     * 根据ids删除文章评论
     * @param commentDeleteVo
     * @return
     */
    @DeleteMapping("/admin/comments")
    public R deleteComment(@RequestBody @Validated CommentDeleteVo commentDeleteVo) {
        // TODO：注意别误删有子评论的评论
        return null;
    }




}
