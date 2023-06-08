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
    private Integer id;
    private String name;
    private String label;

    private Integer parentId;
    private String method;
    private String route;
    private Date createTime;
}
