package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/3/9
 * @Description: 新增文章VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class ArticleAddVo {

    @NotNull(message = "文章标题不允许为空")
    private String title;

    private String cover;

    @NotNull(message = "文章内容不允许为空")
    private String content;

    @NotNull(message = "类型不允许为空")
    @Range(min = 1,max = 2,message = "类型只允许在1-2之间")
    private Integer type;

    private String originUrl;

    @NotNull(message = "置顶状态不允许为空")
    @Range(min = 0,max = 1,message = "置顶状态范围只允许在0-1之间")
    private Integer isTop;

    @NotNull(message = "状态不允许为空")
    @Range(min = 0,max = 1,message = "状态范围只允许在0-1之间")
    private Integer status;

    private List<Integer> categoryIds;
    private List<Integer> tagIds;
}
