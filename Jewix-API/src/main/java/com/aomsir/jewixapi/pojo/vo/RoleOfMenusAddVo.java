package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/6/13
 * @Description: 角色菜单添加VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class RoleOfMenusAddVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "角色id不允许为空")
    @Min(value = 1)
    private Integer roleId;

    @NotNull(message = "菜单列表不允许为空")
    private List<Integer> menuIds;
}
