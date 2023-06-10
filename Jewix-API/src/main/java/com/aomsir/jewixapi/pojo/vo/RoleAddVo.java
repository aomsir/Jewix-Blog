package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/6/10
 * @Description: 角色添加VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class RoleAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "角色名不允许为空")
    private String roleName;

    @NotNull(message = "角色标签不允许为空")
    private String roleLabel;
}
