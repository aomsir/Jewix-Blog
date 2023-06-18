package com.aomsir.jewixapi.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/4/28
 * @Description: 站点信息通用DTO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class WebInfoDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nickname;   //   站长昵称
    private String email;      //   站长邮箱
    private String description;
    private Integer articleCount;
    private Integer commentCount;
    private Long runTime;
    private Integer lastActive;
    private HashMap<String, String> socialInfo;
    private String icp;
    private String police;
}
