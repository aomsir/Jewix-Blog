package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/3/13
 * @Description: 前台获取评论列表
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class CommentBackendPageVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论者邮箱
     */
    private String email;

    /**
     * 评论者名
     */
    private String author;

    /**
     * 状态
     */
    private Integer status;


    /**
     * page
     */
    @NotNull(message = "page不能为空")
    @Min(value = 1, message = "page不能小于1")
    private Integer page;


    /**
     * length
     */
    @NotNull(message = "length不能为空")
    @Range(min = 1, max = 50, message = "length必须为1~50之间")
    private Integer length;
}
