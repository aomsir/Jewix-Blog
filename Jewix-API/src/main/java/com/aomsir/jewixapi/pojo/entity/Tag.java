package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description: 标签实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class Tag extends BaseEntity{
    /**
     * 主键id
     */
    private Long id;

    /**
     * 标签名
     */
    private String tagName;
}
