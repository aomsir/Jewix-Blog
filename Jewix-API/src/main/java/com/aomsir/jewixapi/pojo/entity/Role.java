package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description: 角色实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    private Integer id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色标签
     */
    private String roleLabel;


    /**
     * 角色创建时间
     */
    private Date createTime;

    /**
     * 角色修改时间
     */
    private Date updateTime;
}
