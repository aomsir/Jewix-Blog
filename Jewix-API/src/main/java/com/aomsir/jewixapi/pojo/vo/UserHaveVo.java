package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/3/6
 * @Description: 邮箱or用户名是否存在VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class UserHaveVo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户昵称
     */
    private String nickname;


    /**
     * 用户邮箱
     */
    @Pattern(regexp = "^([A-Za-z0-9_\\-\\.\\u4e00-\\u9fa5])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,8})$",message = "邮箱格式不对嗷")
    private String email;
}
