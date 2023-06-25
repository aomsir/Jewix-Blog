package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/3/16
 * @Description: 评论添加VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class CommentAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 作者名
     */
    @NotBlank(message = "作者不允许为空")
    private String author;


    /**
     * 评论内容
     */
    @NotBlank(message = "内容不允许为空")
    private String content;


    /**
     * 评论邮箱
     */
    @NotBlank(message = "邮箱不允许为空")
    private String email;

    /**
     * 评论者网站
     */
    private String url;


    /**
     * 评论类型
     */
    @NotNull(message = "类型不允许为空")
    @Range(min = 1,max = 50)
    private Integer type;


    /**
     * 目标id
     */
    @NotNull(message = "目标不允许为空")
    @Range(min = 1)
    private Long targetId;

    /**
     * 父级id
     */
    @NotNull(message = "父级评论不能为空")
    private Long parentId;

    /**
     * 一级评论id
     */
    @NotNull(message = "permId不能为空")
    private Long permId;
}
