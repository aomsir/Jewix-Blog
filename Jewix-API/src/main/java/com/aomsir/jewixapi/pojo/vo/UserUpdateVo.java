package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/3/6
 * @Description: 用户更新VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class UserUpdateVo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    @NotNull(message = "id不允许为空")
    @Range(min = 10000,max = 99999)
    private Long id;


    /**
     * 用户uuid
     */
    @NotNull(message = "uuid不允许为空")
    private String uuid;


    /**
     * 用户昵称
     */
    @NotNull(message = "昵称不允许位空")
    private String nickname;

    /**
     * 用户邮箱
     */
    @NotNull(message = "邮箱不允许为空")
    @Pattern(regexp = "^([A-Za-z0-9_\\-\\.\\u4e00-\\u9fa5])+\\@([A-Za-z0-9_\\-\\.])+\\.([A-Za-z]{2,8})$",message = "邮箱格式不对嗷")
    private String email;


    /**
     * 用户描述
     */
    @NotNull(message = "描述不允许为空")
    private String description;


    /**
     * 用户密码
     */
    private String password;


    /**
     * 用户站点
     */
    @Pattern(regexp = "^(http(s)?:\\/\\/)[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+(:[0-9]{1,5})?[-a-zA-Z0-9()@:%_\\\\\\+\\.~#?&//=]*$",message = "网址格式不对嗷")
    private String webSite;
}
