package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

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

    @NotNull(message = "作者不允许为空")
    private String author;

    @NotNull(message = "内容不允许为空")
    private String content;

    @NotNull(message = "邮箱不允许为空")
    private String email;

    private String url;

    @NotNull(message = "类型不允许为空")
    @Range(min = 1,max = 2)
    private Integer type;

    @NotNull(message = "目标不允许为空")
    @Range(min = 1)
    private Long targetId;

    @NotNull(message = "父级评论不能为空")
    private Long parentId;

    @NotNull(message = "permId不能为空")
    private Long permId;
}
