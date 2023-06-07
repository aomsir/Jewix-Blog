package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

/**
 * @Author: Aomsir
 * @Date: 2023/2/24
 * @Description: 文章实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class Article extends BaseEntity{
    private Long id;
    private String uuid;
    private String title;
    private String cover;
    private String content;
    private Integer type;
    private String originUrl;
    private Integer views;
    private Integer isTop;
    private Integer isDelete;
    private String description;

}
