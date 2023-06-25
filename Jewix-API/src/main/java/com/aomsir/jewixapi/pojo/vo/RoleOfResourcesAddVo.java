package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/6/13
 * @Description: 角色资源分配VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class RoleOfResourcesAddVo implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 角色id
     */
    @NotNull(message = "角色id不允许为空")
    @Min(value = 1)
    private Integer roleId;


    /**
     * 资源id列表
     */
    @NotNull(message = "资源列表不允许为空")
    private List<Integer> resourceIds;
}
