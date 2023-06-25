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
public class Article extends BaseEntity {
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
     * 文章内容
     */
    private String content;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 文章源地址
     */
    private String originUrl;

    /**
     * 文章浏览量
     */
    private Integer views;

    /**
     * 是否置顶
     */
    private Integer isTop;

    /**
     * 是否逻辑删除
     */
    private Integer isDelete;

    /**
     * 文章描述
     */
    private String description;

}
