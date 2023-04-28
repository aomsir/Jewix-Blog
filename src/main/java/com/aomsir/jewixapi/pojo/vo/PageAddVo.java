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

    @NotNull(message = "页面名称不能为空")
    private String name;

    @NotNull(message = "页面模版不能为空")
    @Range(min = 1, max = 4, message = "页面模版类型不合法")
    private Integer model;     // 模版类型 0-默认,1-友链,2-留言板,3-时光机,4-文章归档

    @NotNull(message = "页面作者不能为空")
    private String author;

    @NotNull(message = "路径不能为空")
    private Integer omit;

    @NotNull(message = "页面内容不能为空")
    private String content;
}
