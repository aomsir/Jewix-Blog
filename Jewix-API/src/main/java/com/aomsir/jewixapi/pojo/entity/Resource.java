package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Aomsir
 * @Date: 2023/6/8
 * @Description: 资源实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class Resource implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 资源名
     */
    private String name;

    /**
     * 资源标签
     */
    private String label;

    /**
     * 父级id
     */
    private Integer parentId;

    /**
     * 方法
     */
    private String method;

    /**
     * 路由
     */
    private String route;

    /**
     * 创建时间
     */
    private Date createTime;
}
