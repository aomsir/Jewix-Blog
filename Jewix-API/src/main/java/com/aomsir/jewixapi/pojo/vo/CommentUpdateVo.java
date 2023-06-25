package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/3/16
 * @Description: 评论更新VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class CommentUpdateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @NotNull(message = "评论ID不能为空")
    @Range(min = 1, message = "评论ID必须大于0")
    private Long id;


    /**
     * 评论者个人网站
     */
    private String url;


    /**
     * 评论内容
     */
    private String content;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 评论者名
     */
    private String author;


    /**
     * 评论者邮箱
     */
    private String email;

}
