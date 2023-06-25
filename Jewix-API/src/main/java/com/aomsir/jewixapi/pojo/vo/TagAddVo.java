package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/2/23
 * @Description: 添加标签VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class TagAddVo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 标签名
     */
    @NotNull(message = "标签名不允许为空")
    private String tagName;
}
