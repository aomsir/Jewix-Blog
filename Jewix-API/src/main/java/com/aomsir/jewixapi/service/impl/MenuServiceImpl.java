package com.aomsir.jewixapi.service.impl;


import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.mapper.MenuMapper;
import com.aomsir.jewixapi.pojo.dto.MenuListPageDTO;
import com.aomsir.jewixapi.pojo.entity.Menu;
import com.aomsir.jewixapi.service.MenuService;
import com.aomsir.jewixapi.util.PageUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/6/8
 * @Description: 菜单业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public PageUtils searchMenusByPage(Map<String, Object> param) {
        // 查询父级
        int count = this.menuMapper.queryMenuCountsOfParentByPage(param);
        ArrayList<Menu> list = null;
        if (count > 0) {
            list = this.menuMapper.queryMenuOfParentByPage(param);
        }

        ArrayList<MenuListPageDTO> dtoList = new ArrayList<>();
        if (list != null) {
            for (Menu menu : list) {
                MenuListPageDTO pageDTO = new MenuListPageDTO();
                BeanUtil.copyProperties(menu, pageDTO);
                List<Menu> sonList = this.menuMapper.queryMenusByParentId(menu.getId());
                pageDTO.setSonList(sonList);
                dtoList.add(pageDTO);
            }
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        return new PageUtils(dtoList,count,start,length);
    }
}
