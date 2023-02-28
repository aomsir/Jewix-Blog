package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @Author: Aomsir
 * @Date: 2023/2/27
 * @Description: 相册添加VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class PhotoUpdateVo {

    @NotNull(message = "type不允许为空")
    @Range(min = 0,max = 3)
    private Integer type;
}
