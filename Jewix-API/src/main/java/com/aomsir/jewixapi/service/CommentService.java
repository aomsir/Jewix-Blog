package com.aomsir.jewixapi.service;

import com.aomsir.jewixapi.pojo.vo.CommentAddVo;
import com.aomsir.jewixapi.pojo.vo.CommentUpdateStatusVo;
import com.aomsir.jewixapi.pojo.vo.CommentUpdateVo;
import com.aomsir.jewixapi.util.PageUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


/**
 * @Author: Aomsir
 * @Date: 2023/6/26
 * @Description: 评论业务接口
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface CommentService {

    /**
     * 后台分页查询评论列表
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchBackendCommentListByPage(Map<String, Object> param);


    /**
     * 前台分页查询评论列表
     * @param param 参数列表
     * @return 分页数据
     */
    PageUtils searchFrontCommentListByPage(Map<String, Object> param);


    /**
     * 新增评论
     * @param commentAddVo 封装对象
     * @param request request对象
     * @return 新增所影响的行数
     */
    int addComment(CommentAddVo commentAddVo, HttpServletRequest request);

    /**
     * 更新评论
     * @param commentUpdateVo 封装对象
     * @return 更新所影响的行数
     */
    int updateComment(CommentUpdateVo commentUpdateVo);


    /**
     * 删除评论
     * @param list 评论id列表
     * @return 删除所影响的行数
     */
    int deleteComment(List<Long> list);


    /**
     * 更新评论状态
     * @param commentUpdateStatusVo 封装对象
     * @return 更新所影响的行数
     */
    int updateCommentStatus(CommentUpdateStatusVo commentUpdateStatusVo);
}
