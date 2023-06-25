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

    /**
     * 主键id
     */
    private Long id;

    /**
     * 页面uuid
     */
    private String uuid;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 页面标题
     */
    private String title;

    /**
     * 页面内容
     */
    private String content;

    /**
     * 页面描述
     */
    private String description;

    /**
     * 页面路径
     */
    private String omit;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 页面访问
     */
    private Long views;

    /**
     * 页面所属的用户
     */
    private String userName;
}
