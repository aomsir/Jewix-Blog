package com.aomsir.jewixapi.pojo.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;



/**
 * @Author: Aomsir
 * @Date: 2023/2/19
 * @Description: 通用实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 创建时间
     */
    public Date createTime;

    /**
     * 更新时间
     */
    public Date updateTime;

    /**
     * 状态
     */
    public Integer status;

}
