package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/3/21
 * @Description: 页面添加VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class PageAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "页面标题不允许为空")
    private String title;

    @NotNull(message = "页面内容不允许为空")
    private String content;

    @NotNull(message = "页面描述不允许为空")
    private String description;

    @NotNull(message = "类型不允许为空")
    @Range(min = 1,max = 5)
    private Integer type;
}
