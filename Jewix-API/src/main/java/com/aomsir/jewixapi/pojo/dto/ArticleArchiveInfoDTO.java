package com.aomsir.jewixapi.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/4/28
 * @Description: 归档页面信息
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class ArticleArchiveInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 分类数量
     */
    private Integer categoryCount;

    /**
     * 标签数量
     */
    private Integer tagCount;
}
