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

    private Integer page;
    private Integer length;
    private String roleName;

}
