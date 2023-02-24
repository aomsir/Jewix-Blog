package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author: Aomsir
 * @Date: 2023/2/24
 * @Description:
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class TagUpdateVo {

    @NotNull(message = "id不能为空")
    @Min(value = 1, message = "id不能小于1")
    private Long id;

    @NotNull(message = "length不能为空")
    private String tagName;
}
