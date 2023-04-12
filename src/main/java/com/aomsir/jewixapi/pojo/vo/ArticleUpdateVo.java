package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

/**
 * @Author: Aomsir
 * @Date: 2023/3/12
 * @Description: 文章修改VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

// TODO:添加字段校验
@Data
public class ArticleUpdateVo {
    private Long id;
    private String uuid;
    private String title;
    private String cover;
    private String content;
    private String description;
    private Integer type;
    private String originUrl;
    private Integer views;
    private Integer isTop;
    private Integer isDelete;
}
