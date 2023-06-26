package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;


/**
 * @Author: Aomsir
 * @Date: 2023/3/7
 * @Description: 网站基础设置实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class WebConfig extends BaseEntity{

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 配置信息
     */
    private String config;
}
