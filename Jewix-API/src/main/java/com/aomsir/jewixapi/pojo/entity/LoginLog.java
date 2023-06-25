package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Aomsir
 * @Date: 2023/4/23
 * @Description: 登录日志实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class LoginLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 登录者ip
     */
    private String ip;

    /**
     * 位置
     */
    private String location;

    /**
     * 登录时间
     */
    private Date operateTime;
}
