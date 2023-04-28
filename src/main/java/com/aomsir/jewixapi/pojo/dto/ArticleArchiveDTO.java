package com.aomsir.jewixapi.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Aomsir
 * @Date: 2023/4/28
 * @Description:
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class ArticleArchiveDTO implements Serializable {
    private String title;
    private Date createTime;
}
