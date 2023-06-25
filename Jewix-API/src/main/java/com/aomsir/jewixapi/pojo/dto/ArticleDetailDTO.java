package com.aomsir.jewixapi.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/3/12
 * @Description: 文章详情DTO实体对象
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class ArticleDetailDTO implements Serializable {
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
     * 文章类型
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
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 分类列表
     */
    private List<String> categories;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 标签id列表
     */
    private List<Long> tagIds;

    /**
     * 分类id列表
     */
    private List<Long> categoryIds;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 上篇文章uuid
     */
    private String lastUuid;

    /**
     * 下篇文章uuid
     */
    private String nextUuid;

}
