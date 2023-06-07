package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/3/21
 * @Description: 页面更新VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class PageUpdateVo implements Serializable  {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "页面ID不能为空")
    @Range(min = 1, message = "页面ID不合法")
    private Long id;

    @NotNull(message = "uuid不允许为空")
    private String uuid;

    private String title;
    private String content;
    private String description;
    private String omit;
    private Integer type;
    private Long views;
}
