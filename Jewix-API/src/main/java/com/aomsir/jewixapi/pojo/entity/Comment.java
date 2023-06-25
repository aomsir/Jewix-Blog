package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

/**
 * @Author: Aomsir
 * @Date: 2023/3/13
 * @Description: 评论实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class Comment extends BaseEntity {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 评论作者名
     */
    private String author;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论邮箱
     */
    private String email;

    /**
     * 评论者个人网站链接
     */
    private String url;

    /**
     * 评论者ip
     */
    private String ip;

    /**
     * 评论者地理位置
     */
    private String location;


    /**
     * 评论者设备Agent
     */
    private String agent;

    /**
     * 评论类型 - 1-文章、21-时光机、22-友人账、23-留言板、24-关于
     */
    private Integer type;

    /**
     * 目标id
     */
    private Long targetId;

    /**
     * 父级id,0为根评论
     */
    private Long parentId;

    /**
     * 一级评论id,0为根评论
     */
    private Long permId;

    /**
     * 是否是博主
     */
    private boolean isBlogger;
}
