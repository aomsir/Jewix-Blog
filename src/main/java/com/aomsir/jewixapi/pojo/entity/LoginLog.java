package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Aomsir
 * @Date: 2023/4/23
 * @Description: 登录日志实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class LoginLog {
    private Long id;
    private Long userId;
    private String ip;
    private String location;
    private Date operateTime;
}
