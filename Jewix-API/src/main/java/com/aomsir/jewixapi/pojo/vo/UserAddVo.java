package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/3/6
 * @Description: 用户添加VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class UserAddVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "昵称不允许位空")
    private String nickname;

    @NotNull(message = "邮箱不允许为空")
    @Pattern(regexp = "^([A-Za-z0-9_\\-\\.\\u4e00-\\u9fa5])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,8})$",message = "邮箱格式不对嗷")
    private String email;

    @NotNull(message = "密码不允许为空")
    @Size(min = 6,max = 20,message = "长度只允许在6-20位嗷")
    private String password;

    @NotNull(message = "描述不允许为空")
    private String description;


    @Pattern(regexp = "^(http(s)?:\\/\\/)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+(:[0-9]{1,5})?[-a-zA-Z0-9()@:%_\\\\\\+\\.~#?&//=]*$",message = "网址格式不对嗷")
    private String webSite;
}
