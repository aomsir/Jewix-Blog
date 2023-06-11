package com.aomsir.jewixapi.pojo.dto;

import com.aomsir.jewixapi.pojo.entity.Menu;
import lombok.Data;

import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/6/11
 * @Description: 菜单分页列表获取DTO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class MenuListPageDTO extends Menu {
    private List<Menu> sonList;
}
