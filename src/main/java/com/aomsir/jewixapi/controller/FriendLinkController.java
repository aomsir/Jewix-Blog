package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.pojo.vo.FriendLinkPageVo;
import com.aomsir.jewixapi.service.FriendLinkService;
import com.aomsir.jewixapi.utils.PageUtils;
import com.aomsir.jewixapi.utils.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/2/26
 * @Description: 友情链接控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@RestController
public class FriendLinkController {

    @Resource
    private FriendLinkService friendLinkService;


    /**
     * 分页查询友链列表
     * @param friendLinkPageVo
     * @return
     */
    @PostMapping("/friend-link/list")
    public R getFriendLinksByPage(@RequestBody @Validated FriendLinkPageVo friendLinkPageVo) {
        Map<String, Object> param = BeanUtil.beanToMap(friendLinkPageVo);
        int page = (Integer) param.get("page");
        int length = (Integer) param.get("length");
        int start = (page - 1) * length;
        param.put("start",start);
        PageUtils pageUtils = this.friendLinkService.searchFriendLinkListByPage(param);

        return R.ok()
                .put("result",pageUtils);
    }
}
