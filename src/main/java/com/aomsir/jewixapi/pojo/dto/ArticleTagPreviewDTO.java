package com.aomsir.jewixapi.pojo.dto;

import com.aomsir.jewixapi.pojo.entity.BaseEntity;
import lombok.Data;

/**
 * @Author: Aomsir
 * @Date: 2023/2/24
 * @Description: 预览文章DTO对象
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class ArticleTagPreviewDTO extends BaseEntity {
    private Long id;
    private String title;
    private String cover;
    private String content;
    private Integer views;
    private Integer isTop;
}
