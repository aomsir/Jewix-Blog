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

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 父级菜单id
     */
    private Integer parentId;

    /**
     * 菜单名
     */
    private String name;

    /**
     * 菜单icon名
     */
    private String iconName;

    /**
     * 菜单路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String componentPath;

    /**
     * 菜单类型
     */
    private Integer type;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
