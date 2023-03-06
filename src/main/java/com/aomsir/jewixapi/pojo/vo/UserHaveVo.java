package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @Author: Aomsir
 * @Date: 2023/3/6
 * @Description: 邮箱or用户名是否存在VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class UserHaveVo {

    private String nickname;

    @Pattern(regexp = "^([A-Za-z0-9_\\-\\.\\u4e00-\\u9fa5])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,8})$",message = "邮箱格式不对嗷")
    private String email;
}
