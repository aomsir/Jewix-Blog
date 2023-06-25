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

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户uuid
     */
    private String uuid;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户描述
     */
    private String description;

    /**
     * 用户站点
     */
    private String webSite;
}
