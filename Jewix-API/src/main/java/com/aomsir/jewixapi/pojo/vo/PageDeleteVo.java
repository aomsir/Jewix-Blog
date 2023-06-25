package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/3/21
 * @Description: 页面删除VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class PageDeleteVo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 页面id
     */
    @NotNull(message = "页面ID不能为空")
    @Range(min = 1, message = "页面ID不合法")
    private Integer id;


    /**
     * 路径
     */
    @NotNull(message = "路径不能为空")
    @Range(min = 1, max = 4,message = "路径不合法")
    private Integer omit;
}
