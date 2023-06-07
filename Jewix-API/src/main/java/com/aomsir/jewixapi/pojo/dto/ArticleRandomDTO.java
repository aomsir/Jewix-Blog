package com.aomsir.jewixapi.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/4/28
 * @Description: 随机文章列表DTO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class ArticleRandomDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String title;
    String cover;
    private String uuid;
}
