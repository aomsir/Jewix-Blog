package com.aomsir.jewixapi.pojo.dto;

import com.aomsir.jewixapi.pojo.entity.Resource;
import lombok.Data;

import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/6/11
 * @Description: 资源分页列表获取DTO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class ResourceListPageDTO extends Resource {

    /**
     * 子接口资源列表
     */
    private List<Resource> resourceSons;
}
