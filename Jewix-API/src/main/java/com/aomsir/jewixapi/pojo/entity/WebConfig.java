package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/3/7
 * @Description: 网站基础设置实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class WebConfig extends BaseEntity{
    private Integer id;
    private String config;
}
