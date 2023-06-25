package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/2/26
 * @Description: 分页查询友链VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class FriendLinkPageVo implements Serializable {

    private static final long serialVersionUID = 1L;


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
    @Range(min = 10, max = 50, message = "length必须为10~50之间")
    private Integer length;


    /**
     * 位置
     */
    @NotNull(message = "location不能为空")
    @Range(min = 1,max = 3)
    private Integer location;
}
