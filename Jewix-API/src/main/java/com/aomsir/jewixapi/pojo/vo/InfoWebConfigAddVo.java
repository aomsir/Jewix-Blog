package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/3/7
 * @Description: 网站通用信息新增VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class InfoWebConfigAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "标题不允许为空")
    private String title;

    @NotNull(message = "描述不允许为空")
    private String description;

    @NotNull(message = "关键词不允许为空")
    private List<String> keyword;

    @NotNull(message = "站点链接不允许为空")
    private String webSite;

    @NotNull(message = "建站日期不允许为空")
    private Date buildDate;

    @NotNull(message = "类型不能为空")
    private Integer type;
}
