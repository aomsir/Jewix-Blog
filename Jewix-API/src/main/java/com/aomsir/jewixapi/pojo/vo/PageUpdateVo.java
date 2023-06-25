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


    /**
     * 主键id
     */
    @NotNull(message = "页面ID不能为空")
    @Range(min = 1, message = "页面ID不合法")
    private Long id;


    /**
     * 页面uuid
     */
    @NotNull(message = "uuid不允许为空")
    private String uuid;


    /**
     * 页面标题
     */
    private String title;

    /**
     * 页面内容
     */
    private String content;

    /**
     * 页面描述
     */
    private String description;

    /**
     * 页面路径
     */
    private String omit;

    /**
     * 页面类型
     */
    private Integer type;

    /**
     * 页面浏览量
     */
    private Long views;
}
