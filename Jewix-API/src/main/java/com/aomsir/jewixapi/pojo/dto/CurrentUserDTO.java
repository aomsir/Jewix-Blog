package com.aomsir.jewixapi.pojo.dto;

import com.aomsir.jewixapi.pojo.entity.User;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/6/13
 * @Description: 当前用户DTO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class CurrentUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 用户
     */
    private User user;

    /**
     * 菜单分页列表
     */
    private List<MenuListPageDTO> menuListPageDTO;
}
