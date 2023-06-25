package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/3/9
 * @Description: 新增文章VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class ArticleAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文章标题
     */
    @NotNull(message = "文章标题不允许为空")
    private String title;

    /**
     * 文章封面
     */
    private String cover;

    /**
     * 文章内容
     */
    @NotNull(message = "文章内容不允许为空")
    private String content;

    /**
     * 文章类型
     */
    @NotNull(message = "类型不允许为空")
    @Range(min = 1,max = 2,message = "类型只允许在1-2之间")
    private Integer type;


    /**
     * 文章描述
     */
    private String description;

    /**
     * 源文章地址
     */
    private String originUrl;

    /**
     * 是否置顶
     */
    @NotNull(message = "置顶状态不允许为空")
    @Range(min = 0,max = 1,message = "置顶状态范围只允许在0-1之间")
    private Integer isTop;

    /**
     * 状态
     */
    @NotNull(message = "状态不允许为空")
    @Range(min = 0,max = 1,message = "状态范围只允许在0-1之间")
    private Integer status;

    /**
     * 分类id列表
     */
    private List<Long> categoryIds;

    /**
     * 标签id列表
     */
    private List<Long> tagIds;
}
