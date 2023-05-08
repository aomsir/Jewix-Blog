package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

/**
 * @Author: Aomsir
 * @Date: 2023/3/21
 * @Description: 页面实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class Page extends BaseEntity {
    private Long id;
    private String uuid;
    private Long userId;
    private String title;
    private String content;
    private String description;
    private String omit;
    private Integer type;
    private Long views;
    private String userName;
}
