package com.aomsir.jewixapi.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/4/28
 * @Description: 归档页面信息
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class ArticleArchiveInfoDTO implements Serializable {
    private Integer articleCount;
    private Integer commentCount;
    private Integer categoryCount;
    private Integer tagCount;
}
