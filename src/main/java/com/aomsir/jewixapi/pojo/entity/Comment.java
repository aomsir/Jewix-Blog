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
    private Long id;
    private String author;
    private String content;     // 评论内容
    private String email;       // 评论邮箱
    private String url;         // 评论者个人网站链接
    private String ip;          // 评论者ip
    private String location;    // 评论者地理位置
    private String agent;       // 评论者设备Agent
    private Integer type;       // 评论类型 - 1-文章、21-时光机、22-友人账、23-留言板、24-关于
    private Long targetId;      // 文章id或者页面id
    private Long parentId;      // 父级id,0为根评论
    private Long permId;        // 一级评论id,0为根评论
}
