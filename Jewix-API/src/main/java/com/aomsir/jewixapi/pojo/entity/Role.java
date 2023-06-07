package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description: 角色实体类
 * @Email: info@say521.cn
 * @GitHub: https://github.com/aomsir
 */

@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;
    // TODO: 详细设计
    private String name;
}
