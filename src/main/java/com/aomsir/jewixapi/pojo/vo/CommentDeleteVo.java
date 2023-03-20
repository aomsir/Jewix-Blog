package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * @Author: Aomsir
 * @Date: 2023/3/16
 * @Description: 评论删除VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class CommentDeleteVo {

    @NotNull(message = "ids不允许为空")
    private ArrayList<Long> ids;
}
