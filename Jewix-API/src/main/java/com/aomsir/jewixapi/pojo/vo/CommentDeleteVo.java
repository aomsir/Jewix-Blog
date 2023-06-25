package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @Author: Aomsir
 * @Date: 2023/3/16
 * @Description: 评论删除VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class CommentDeleteVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 评论id列表
     */
    @NotNull(message = "ids不允许为空")
    private ArrayList<Long> ids;
}
