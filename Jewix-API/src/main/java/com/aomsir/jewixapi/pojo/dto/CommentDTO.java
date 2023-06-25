package com.aomsir.jewixapi.pojo.dto;

import com.aomsir.jewixapi.pojo.entity.Comment;
import lombok.Data;

import java.util.ArrayList;

/**
 * @Author: Aomsir
 * @Date: 2023/3/16
 * @Description: 评论返回DTO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class CommentDTO extends Comment {
    private static final long serialVersionUID = 1L;

    /**
     * 子评论列表
     */
    private ArrayList<Comment> childList;
}
