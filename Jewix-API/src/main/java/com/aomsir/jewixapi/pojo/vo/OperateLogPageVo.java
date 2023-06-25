package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Author: Aomsir
 * @Date: 2023/6/14
 * @Description: 分页获取操作日志VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class OperateLogPageVo {

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
     * 操作模块
     */
    private String optModule;
}
