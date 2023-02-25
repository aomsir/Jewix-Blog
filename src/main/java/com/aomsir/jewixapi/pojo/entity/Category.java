package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

/**
 * @Author: Aomsir
 * @Date: 2023/2/25
 * @Description: 分类实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class Category extends BaseEntity{
    private Long id;
    private String categoryName;
    private Long parentId;
}
