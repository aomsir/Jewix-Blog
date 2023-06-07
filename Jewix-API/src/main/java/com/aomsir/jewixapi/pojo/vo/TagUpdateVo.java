package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/2/24
 * @Description: 更新标签VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class TagUpdateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    private Long id;

    @NotNull(message = "length不能为空")
    private String tagName;
}
