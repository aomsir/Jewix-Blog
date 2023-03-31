package com.aomsir.jewixapi.pojo.dto;

import com.aomsir.jewixapi.pojo.entity.Category;
import lombok.Data;

import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/3/29
 * @Description: 分类包装DTO实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class CategoryListDTO {
    private Long id;
    private String categoryName;
    private List<Category> sonList;
}
