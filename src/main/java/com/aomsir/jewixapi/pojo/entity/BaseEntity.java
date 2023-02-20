package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Aomsir
 * @Date: 2023/2/19
 * @Description: 实体类通用信息
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

public class BaseEntity {
    public Date createTime;
    public Date updateTime;
    public Integer status;
}
