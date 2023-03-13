package com.aomsir.jewixapi.controller;

import com.aomsir.jewixapi.pojo.vo.CommentBackendPageVo;
import com.aomsir.jewixapi.pojo.vo.CommentFrontVo;
import com.aomsir.jewixapi.utils.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: Aomsir
 * @Date: 2023/3/13
 * @Description: 评论控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@RestController
public class CommentController {

    /**
     * 后台无限制获取评论列表
     * @param commentBackendPageVo
     * @return
     */
    @GetMapping("/admin/comments")
    public R getCommentsBackendListByPage(@RequestBody @Validated CommentBackendPageVo commentBackendPageVo) {

        return null;
    }

    /**
     * 前台根据id与类型获取评论列表
     * @param commentFrontVo
     * @return
     */
    @GetMapping("/comments")
    public R getCommentsById(@RequestBody @Validated CommentFrontVo commentFrontVo) {

        return null;
    }


    @PostMapping("/admin/comments")
    public R addComment() {

        return null;
    }

    @PutMapping("/admin/comments")
    public R updateComment() {
        // 内容状态等等
        return null;
    }

    @DeleteMapping("/admin/comments")
    public R deleteComment() {

        return null;
    }




}
