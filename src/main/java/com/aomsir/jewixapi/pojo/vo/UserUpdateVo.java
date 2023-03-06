package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @Author: Aomsir
 * @Date: 2023/3/6
 * @Description: 用户更新VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class UserUpdateVo {

    @NotNull(message = "id不允许为空")
    @Range(min = 10000,max = 99999)
    private Long id;

    @NotNull(message = "uuid不允许为空")
    private String uuid;


    @NotNull(message = "昵称不允许位空")
    private String nickname;

    @NotNull(message = "邮箱不允许为空")
    @Pattern(regexp = "^([A-Za-z0-9_\\-\\.\\u4e00-\\u9fa5])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,8})$",message = "邮箱格式不对嗷")
    private String email;

    @NotNull(message = "描述不允许为空")
    private String description;

    private String password;

    @Pattern(regexp = "^(http(s)?:\\/\\/)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+(:[0-9]{1,5})?[-a-zA-Z0-9()@:%_\\\\\\+\\.~#?&//=]*$",message = "网址格式不对嗷")
    private String webSite;
}
