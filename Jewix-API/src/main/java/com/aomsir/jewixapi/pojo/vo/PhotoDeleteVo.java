package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/3/1
 * @Description: 相册删除VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class PhotoDeleteVo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 文件名
     */
    @NotNull(message = "文件名不允许为空")
    private String fileName;


    /**
     * 类型
     */
    @NotNull(message = "类型不允许为空")
    @Range(min = 0, max = 3)
    private Integer type;
}
