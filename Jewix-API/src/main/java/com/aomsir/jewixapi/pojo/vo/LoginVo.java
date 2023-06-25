package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/2/19
 * @Description: 登录VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class LoginVo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 用户名(邮箱)
     */
    @Pattern(regexp = "^([A-Za-z0-9_\\-\\.\\u4e00-\\u9fa5])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,8})$")
    @NotNull
    private String username;


    /**
     * 密码
     */
    @NotNull
    private String password;
}
