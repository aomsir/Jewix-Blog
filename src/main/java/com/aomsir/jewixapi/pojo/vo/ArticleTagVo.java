package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: Aomsir
 * @Date: 2023/2/24
 * @Description: 根据标签名获取文章列表对象
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class ArticleTagVo {

    @NotNull
    private String tagName;
}
