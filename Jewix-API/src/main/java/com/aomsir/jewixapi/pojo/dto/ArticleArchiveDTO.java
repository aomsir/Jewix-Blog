package com.aomsir.jewixapi.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Aomsir
 * @Date: 2023/4/28
 * @Description: 文章归档DTO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class ArticleArchiveDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 文章uuid
     */
    private String uuid;

    /**
     * 文章标题
     */
    private String title;

    /**
     * 文章创建时间
     */
    private Date createTime;
}
