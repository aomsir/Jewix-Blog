package com.aomsir.jewixapi.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/3/6
 * @Description: 站长用户信息
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class UserConfigDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String uuid;
    private String nickname;
    private String description;
    private String webSite;
}
