package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
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


    /**
     * 网站标题
     */
    @NotNull(message = "标题不允许为空")
    private String title;


    /**
     * 文章描述
     */
    @NotNull(message = "描述不允许为空")
    private String description;


    /**
     * 文章关键词
     */
    @NotNull(message = "关键词不允许为空")
    private List<String> keyword;


    /**
     * 网站链接
     */
    @NotNull(message = "站点链接不允许为空")
    private String webSite;


    /**
     * 建站日期
     */
    @NotNull(message = "建站日期不允许为空")
    private Date buildDate;


    /**
     * ICP备案信息
     */
    private String icp;

    /**
     * 公安备案信息
     */
    private String police;

    /**
     * 社交信息
     */
    private HashMap<String, String> socialInfo;

}
