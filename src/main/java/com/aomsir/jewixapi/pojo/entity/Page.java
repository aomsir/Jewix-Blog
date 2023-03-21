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
    private Integer id;
    private String name;

    private Integer model;     // 模版类型 0-默认,1-友链,2-留言板,3-时光机,4-文章归档

    private String author;

    private Integer omit;
    private String content;
}
