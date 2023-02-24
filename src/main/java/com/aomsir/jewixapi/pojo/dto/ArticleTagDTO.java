package com.aomsir.jewixapi.pojo.dto;

import com.aomsir.jewixapi.pojo.entity.Article;
import com.aomsir.jewixapi.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/2/24
 * @Description: 标签获取文章列表DTO对象
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class ArticleTagDTO {

    private String tagId;
    private String tagName;
    private List<Article> articleList;
}
