package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/3/12
 * @Description: 文章修改VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

// TODO:添加字段校验
@Data
public class ArticleUpdateVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String uuid;
    private String title;
    private String cover;
    private String content;
    private String description;
    private Integer type;
    private String originUrl;
    private Integer views;
    private Integer isTop;
    private Integer isDelete;
    private List<Long> tagIds;
    private List<Long> categoryIds;
}
