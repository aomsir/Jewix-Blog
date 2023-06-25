package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

/**
 * @Author: Aomsir
 * @Date: 2023/2/26
 * @Description: 友情链接实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class FriendLink extends BaseEntity{

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 友链标题
     */
    private String title;

    /**
     * 友链链接
     */
    private String link;

    /**
     * 友链图
     */
    private String photo;

    /**
     * 描述
     */
    private String description;

    /**
     * 友链位置
     */
    private Integer location;
}
