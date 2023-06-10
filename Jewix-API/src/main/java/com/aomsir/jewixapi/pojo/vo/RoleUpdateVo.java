package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author: Aomsir
 * @Date: 2023/6/10
 * @Description: 更新角色VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class RoleUpdateVo {
    @NotNull(message = "角色ID不允许为空")
    @Min(value = 1)
    private Integer id;

    @NotNull(message = "角色名不允许为空")
    private String roleName;

    @NotNull(message = "角色标签不允许为空")
    private String roleLabel;

}
