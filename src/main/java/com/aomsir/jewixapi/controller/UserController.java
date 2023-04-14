package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.pojo.dto.UserConfigDTO;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.pojo.vo.*;
import com.aomsir.jewixapi.service.UserService;
import com.aomsir.jewixapi.utils.PageUtils;
import com.aomsir.jewixapi.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/2/19
 * @Description: 用户控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Api(tags = "用户控制器")
@RestController
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 前台获取站长用户信息
     * @return 站长基本信息
     */
    @ApiOperation(value = "前台获取站长用户信息")
    @GetMapping("/users/10000")
    public R getConfigUser() {
        UserConfigDTO userConfigDTO = this.userService.searchConfigUser();
        return R.ok()
                .put("result",userConfigDTO);
    }

    /**
     * 分页查询用户信息接口
     * @param userPageVo 分页获取用户VO对象
     * @return 用户分页列表
     */
    @ApiOperation(value = "分页查询用户列表")
    @GetMapping("/admin/users")
    public R getUserPage(@Validated UserPageVo userPageVo) {
        Map<String, Object> param = BeanUtil.beanToMap(userPageVo);

        int page = userPageVo.getPage();
        int length = userPageVo.getLength();
        int start = (page - 1) * length;
        param.put("start", start);
        PageUtils pageUtils = this.userService.searchUserByPage(param);
        return R.ok()
                .put("result", pageUtils);
    }


    /**
     * 后台根据UUID获取用户个人详细信息
     * @param uuid 用户uuid
     * @return  用户详情
     */
    @ApiOperation(value = "后台根据UUID获取用户个人详细信息")
    @GetMapping("/admin/users/{uuid}")
    public R getBackendUserByUUID(@PathVariable String uuid) {
        User user = this.userService.searchUserByUUID(uuid);
        return R.ok()
                .put("result", user);
    }


    /**
     * 添加/注册新用户接口
     * @param userAddVo 添加用户VO对象
     * @return 新增用户所影响的行数
     */
    @ApiOperation(value = "添加/注册新用户")
    @PostMapping("/admin/users")
    public R addUser(@RequestBody @Validated UserAddVo userAddVo) {
        int role = this.userService.addUser(userAddVo);
        return R.ok()
                .put("role",role);
    }


    /**
     * 更新用户接口
     * @param userUpdateVo 更新用户VO对象
     * @return 更新用户所影响的行数
     */
    @ApiOperation(value = "更新用户接口")
    @PutMapping("/admin/users")
    public R updateUser(@RequestBody @Validated UserUpdateVo userUpdateVo) {
        int role = this.userService.updateUser(userUpdateVo);
        return R.ok()
                .put("role",role);
    }


    /**
     * 查询邮箱与用户名是否已存在接口
     * @param userHaveVo 邮箱/用户名是否存在VO对象
     * @return 所查询的行数
     */
    @ApiOperation(value = "查询邮箱与用户名是否已存在接口")
    @GetMapping("/users/hasUser")
    public R hasUser(@Validated UserHaveVo userHaveVo) {
        int role = this.userService.hasUser(userHaveVo);
        return R.ok()
                .put("role",role);
    }


    /**
     * 根据uuid修改用户状态
     * @param userStatusVo 修改用户状态VO对象
     * @return 更新状态所影响的行数
     */
    @ApiOperation(value = "根据uuid修改用户状态")
    @PutMapping("/admin/users/status")
    public R updateStatus(@RequestBody @Validated UserStatusVo userStatusVo) {
        int role = this.userService.updateStatus(userStatusVo);
        return R.ok()
                .put("role",role);
    }

    /**
     *
     * @return 查询当前登录的用户信息
     */
    @ApiOperation(value = "查询当前登录的用户信息")
    @GetMapping("/users/current")
    public R getCurrentUser() {
        User user = this.userService.searchCurrentUser();
        return R.ok()
                .put("status", user);
    }

    // TODO: 删除接口
}
