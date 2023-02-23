package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: Aomsir
 * @Date: 2023/2/23
 * @Description: 添加标签VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class TagAddVo {

    @NotNull
    private String tagName;
}
