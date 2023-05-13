package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.pojo.entity.FriendLink;
import com.aomsir.jewixapi.pojo.vo.FriendLinkAddVo;
import com.aomsir.jewixapi.pojo.vo.FriendLinkPageVo;
import com.aomsir.jewixapi.pojo.vo.FriendLinkUpdateVo;
import com.aomsir.jewixapi.service.FriendLinkService;
import com.aomsir.jewixapi.utils.PageUtils;
import com.aomsir.jewixapi.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/2/26
 * @Description: 友情链接控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Api(tags = "友情链接控制器")
@RestController
public class FriendLinkController {

    @Resource
    private FriendLinkService friendLinkService;


    /**
     * 分页查询友链列表
     * @param friendLinkPageVo 分页获取友链列表VO对象
     * @return 友链列表
     */
    @ApiOperation(value = "分页查询友链列表")
    @GetMapping("/friend-links/page")
    public R getFriendLinksByPage(@Validated FriendLinkPageVo friendLinkPageVo) {
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
     * @param friendLinkAddVo 添加友链VO对象
     * @return 友链分页列表
     */
    @ApiOperation(value = "添加友情链接")
    @PostMapping("/admin/friend-links")
    public R addFriendLink(@RequestBody @Validated FriendLinkAddVo friendLinkAddVo) {
        Map<String, Object> param = BeanUtil.beanToMap(friendLinkAddVo);
        int role = this.friendLinkService.addFriendLink(param);
        return R.ok()
                .put("role", role);
    }


    /**
     * 更新友情链接信息
     * @param friendLinkUpdateVo 更新友链VO对象
     * @return 更新友链所影响的行数
     */
    @ApiOperation(value = "修改友情链接信息")
    @PutMapping("/admin/friend-links")
    public R updateFriendLink(@RequestBody @Validated FriendLinkUpdateVo friendLinkUpdateVo) {
        Map<String, Object> param = BeanUtil.beanToMap(friendLinkUpdateVo);

        int role = this.friendLinkService.updateFriendLink(param);
        return R.ok()
                .put("role", role);
    }

    /**
     * 根据id获取友情链接详情
     * @param id 友链ID
     * @return 友链详情
     */
    @ApiOperation(value = "根据id获取友情链接详情")
    @GetMapping("/admin/friend-links/{id}")
    public R getFriendLinkInfo(@PathVariable("id") Integer id) {
        FriendLink friendLink = this.friendLinkService.searchFriendLinkInfoById(id);
        return R.ok()
                .put("result",friendLink);
    }


    /**
     * 删除友情链接
     * @param ids 待删除友情链接id列表
     * @return 删除影响的数据库行数
     */
    @ApiOperation(value = "删除友情链接")
    @DeleteMapping("/admin/friend-links")
    public R deleteFriendLink(@RequestParam List<Integer> ids) {
        int role = this.friendLinkService.deleteFriendLinks(ids);
        return R.ok()
                .put("role",role);
    }
}
