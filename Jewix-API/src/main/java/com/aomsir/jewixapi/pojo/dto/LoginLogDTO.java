package com.aomsir.jewixapi.pojo.dto;

import com.aomsir.jewixapi.pojo.entity.LoginLog;
import lombok.Data;

/**
 * @Author: Aomsir
 * @Date: 2023/6/14
 * @Description: 登录日志DTO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class LoginLogDTO extends LoginLog {
    private String nickname;
}
