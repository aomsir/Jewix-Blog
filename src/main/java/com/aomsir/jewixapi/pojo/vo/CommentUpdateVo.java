package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @Author: Aomsir
 * @Date: 2023/3/16
 * @Description: 评论更新VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class CommentUpdateVo {
    @NotNull(message = "评论ID不能为空")
    @Range(min = 1, message = "评论ID必须大于0")
    private Long id;

    private String url;

    private String content;
    private Integer status;

    private String author;

    private String email;

}
