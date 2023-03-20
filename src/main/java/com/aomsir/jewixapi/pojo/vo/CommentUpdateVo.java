package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

/**
 * @Author: Aomsir
 * @Date: 2023/3/16
 * @Description: 评论更新VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class CommentUpdateVo {

    private String content;
    private Integer status;

    private String author;

    private String email;

}
