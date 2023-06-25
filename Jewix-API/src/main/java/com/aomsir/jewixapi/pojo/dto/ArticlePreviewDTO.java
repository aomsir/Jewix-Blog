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
public class ArticlePreviewDTO extends BaseEntity {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 文章uuid
     */
    private String uuid;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章封面
     */
    private String cover;

    /**
     * 文章描述
     */
    private String description;

    /**
     * 文章浏览量
     */
    private Integer views;

    /**
     * 是否置顶
     */
    private Integer isTop;
}
