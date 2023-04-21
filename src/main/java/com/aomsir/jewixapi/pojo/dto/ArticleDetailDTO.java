package com.aomsir.jewixapi.pojo.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/3/12
 * @Description: 文章详情DTO实体对象
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class ArticleDetailDTO {
    private Long id;
    private String uuid;
    private String title;
    private String cover;
    private String content;
    private Integer type;
    private String originUrl;
    private Integer views;
    private Integer isTop;
    private Integer isDelete;
    private Date createTime;
    private Date updateTime;

    private Integer commentCount;

    private List<String> categories;

    private List<String> tags;

    private List<Long> tagIds;
    private List<Long> categoryIds;

    private String userName;

    private String lastUuid;
    private String nextUuid;

}
