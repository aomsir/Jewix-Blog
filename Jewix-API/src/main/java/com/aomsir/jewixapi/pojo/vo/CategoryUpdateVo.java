package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/4/18
 * @Description: 分类修改VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class CategoryUpdateVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @NotNull(message = "id不允许为空")
    private Long id;

    /**
     * 分类名
     */
    @NotNull(message = "分类名不允许为空")
    private String categoryName;

    /**
     * 父分类id
     */
    @NotNull(message = "父类id不允许为空")
    private Long parentId;
}
