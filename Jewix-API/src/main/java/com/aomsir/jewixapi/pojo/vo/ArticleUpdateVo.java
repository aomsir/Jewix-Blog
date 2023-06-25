package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/3/12
 * @Description: 文章修改VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class ArticleUpdateVo implements Serializable {
    private static final long serialVersionUID = 1L;

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
     * 文章描述
     */
    private String description;

    /**
     * 文章类型
     */
    private Integer type;


    /**
     * 文章源地址
     */
    private String originUrl;

    /**
     * 浏览量
     */
    private Integer views;

    /**
     * 是否置顶
     */
    private Integer isTop;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 逻辑删除
     */
    private Integer isDelete;

    /**
     * 标签id列表
     */
    private List<Long> tagIds;

    /**
     * 分类id列表
     */
    private List<Long> categoryIds;
}
