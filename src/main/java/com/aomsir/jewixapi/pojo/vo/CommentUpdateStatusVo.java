package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author: Aomsir
 * @Date: 2023/3/21
 * @Description: 评论更新状态VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class CommentUpdateStatusVo {
    @NotNull(message = "评论id不能为空")
    private Long id;

    @NotNull(message = "评论状态不能为空")
    @Min(value = 0,message = "评论状态不合法")
    private Integer status;
}
