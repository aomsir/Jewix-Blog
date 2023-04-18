package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: Aomsir
 * @Date: 2023/4/18
 * @Description: 分类修改VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class CategoryUpdateVo {
    @NotNull(message = "id不允许为空")
    private Long id;

    @NotNull(message = "分类名不允许为空")
    private String categoryName;

    @NotNull(message = "父类id不允许为空")
    private Long parentId;
}
