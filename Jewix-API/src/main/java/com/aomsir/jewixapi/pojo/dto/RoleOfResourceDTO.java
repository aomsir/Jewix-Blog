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
    private Integer id;
    private String roleName;
    private String roleLabel;
    private List<ResourceListPageDTO> resourceListPageDTOList;
}
