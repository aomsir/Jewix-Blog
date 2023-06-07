package com.aomsir.jewixapi.pojo.vo;


import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/2/25
 * @Description: 添加分类VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class CategoryAddVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "分类名不能为空")
    private String categoryName;

    @NotNull(message = "父类id不能为空")
    @Min(value = 0)
    private Long parentId;
}
