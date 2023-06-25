package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import java.io.Serializable;


/**
 * @Author: Aomsir
 * @Date: 2023/6/10
 * @Description: 权限分页获取VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class RolePageVo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * page
     */
    private Integer page;

    /**
     * length
     */
    private Integer length;

    /**
     * 角色名
     */
    private String roleName;

}
