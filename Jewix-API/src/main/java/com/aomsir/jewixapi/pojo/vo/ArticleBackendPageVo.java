package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/3/7
 * @Description: 后台文章分页获取VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class ArticleBackendPageVo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 是否置顶
     */
    private Integer isTop;

    /**
     * 状态
     */
    private Integer status;


    /**
     * 类型
     */
    private Integer type;

    /**
     * page
     */
    @NotNull(message = "page不能为空")
    @Min(value = 1, message = "page不能小于1")
    private Integer page;

    /**
     * length
     */
    @NotNull(message = "length不能为空")
    @Range(min = 1, max = 50, message = "length必须为1~50之间")
    private Integer length;
}
