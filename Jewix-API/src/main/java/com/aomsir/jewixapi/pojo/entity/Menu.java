package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Aomsir
 * @Date: 2023/6/11
 * @Description: 菜单实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class Menu implements Serializable {
    private Integer id;
    private Integer parentId;
    private String name;
    private String iconName;
    private String path;
    private String componentPath;
    private Integer type;
    private Date createTime;
    private Date updateTime;
}
