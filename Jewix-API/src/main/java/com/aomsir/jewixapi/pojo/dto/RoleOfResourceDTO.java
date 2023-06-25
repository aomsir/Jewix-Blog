package com.aomsir.jewixapi.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/6/18
 * @Description: 角色相关菜单资源DTO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class RoleOfResourceDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 角色名
     */
    private String roleName;

    /**
     * 角色标签
     */
    private String roleLabel;

    /**
     * 角色对应接口资源列表
     */
    private List<ResourceListPageDTO> resourceListPageDTOList;
}
