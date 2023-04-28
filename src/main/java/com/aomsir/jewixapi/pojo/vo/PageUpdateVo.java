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
public class PageUpdateVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "页面ID不能为空")
    @Range(min = 1, message = "页面ID不合法")
    private Integer id;
    private String name;

    private Integer model;     // 模版类型 0-默认,1-友链,2-留言板,3-时光机,4-文章归档

    private String author;

    private Integer omit;

    private String content;
}
