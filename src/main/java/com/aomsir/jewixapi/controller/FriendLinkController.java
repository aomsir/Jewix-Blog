package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.pojo.entity.FriendLink;
import com.aomsir.jewixapi.pojo.vo.FriendLinkAddVo;
import com.aomsir.jewixapi.pojo.vo.FriendLinkPageVo;
import com.aomsir.jewixapi.pojo.vo.FriendLinkUpdateVo;
import com.aomsir.jewixapi.service.FriendLinkService;
import com.aomsir.jewixapi.utils.PageUtils;
import com.aomsir.jewixapi.utils.R;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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


    /**
     * 添加友情链接
     * @param friendLinkAddVo
     * @return
     */
    @PostMapping("/admin/friend-link/add")
    public R addFriendLink(@RequestBody @Validated FriendLinkAddVo friendLinkAddVo) {
        Map<String, Object> param = BeanUtil.beanToMap(friendLinkAddVo);
        int role = this.friendLinkService.addFriendLink(param);
        return R.ok()
                .put("role", role);
    }


    /**
     * 更新友情链接信息
     * @param friendLinkUpdateVo
     * @return
     */
    @PutMapping("/admin/friend-link/update")
    public R updateFriendLink(@RequestBody @Validated FriendLinkUpdateVo friendLinkUpdateVo) {
        Map<String, Object> param = BeanUtil.beanToMap(friendLinkUpdateVo);

        int role = this.friendLinkService.updateFriendLink(param);
        return R.ok()
                .put("role", role);
    }

    @GetMapping("/friend-link/{id}")
    public R getFriendLinkInfo(@PathVariable("id") Integer id) {
        FriendLink friendLink = this.friendLinkService.findFriendLinkInfoById(id);
        return R.ok()
                .put("result",friendLink);
    }
}
