package com.aomsir.jewixapi.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    /**
     * 站长昵称
     */
    private String nickname;

    /**
     * 站长邮箱
     */
    private String email;

    /**
     * 描述
     */
    private String description;

    /**
     * 文章数量
     */
    private Integer articleCount;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 运行天数
     */
    private Long runTime;

    /**
     * 上次活跃
     */
    private Integer lastActive;

    /**
     * 社交信息
     */
    private HashMap<String, String> socialInfo;

    /**
     * icp备案信息
     */
    private String icp;

    /**
     * 公安备案信息
     */
    private String police;

    /**
     * 站点标题
     */
    private String title;

    /**
     * 网站描述
     */
    private String webDescription;

    /**
     * 网站关键词
     */
    private List<String> keywords;

}
