package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/3/13
 * @Description: 前台根据文章/页面ID获取评论分页列表
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class CommentFrontVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 目标id
     */
    @Min(value = 1)
    @NotNull(message = "目标id不能为空")
    private Long targetId;


    /**
     * 类型
     */
    @Range(min = 0,max = 25)
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
