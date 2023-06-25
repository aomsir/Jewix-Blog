package com.aomsir.jewixapi.constant;


/**
 * @Author: Aomsir
 * @Date: 2023/6/15
 * @Description: 评论业务常量信息
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public interface CommentConstants {
    String COMMENT_TYPE_ERROR = "评论类型异常";
    String PARENT_COMMENT_IS_NULL = "父评论不存在";
    String FIRST_COMMENT_IS_NULL = "一级评论不存在";
    String COMMENT_IS_NULL = "评论不存在";
    String COMMENT_DELETE_LIST_IS_NULL = "评论删除列表为空";
    String COMMENT_HAS_SON = "分类还有子分类,请先处理";
}
