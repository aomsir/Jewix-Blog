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
    private Integer id;
    private String title;
    private String link;
    private String photo;
    private String description;
    private Integer location;
}
