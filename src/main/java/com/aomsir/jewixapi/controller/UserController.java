package com.aomsir.jewixapi.controller;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.pojo.dto.UserConfigDTO;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.pojo.vo.LoginVo;
import com.aomsir.jewixapi.pojo.vo.UserAddVo;
import com.aomsir.jewixapi.pojo.vo.UserPageVo;
import com.aomsir.jewixapi.pojo.vo.UserUpdateVo;
import com.aomsir.jewixapi.service.UserService;
import com.aomsir.jewixapi.utils.PageUtils;
import com.aomsir.jewixapi.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/2/19
 * @Description: 用户控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Api("用户接口")
@RestController
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 前台获取站长用户信息
     * @return
     */
    @GetMapping("/users/10000")
    public R getConfigUser() {
        UserConfigDTO userConfigDTO = this.userService.searchConfigUser();
        return R.ok()
                .put("result",userConfigDTO);
    }

    /**
     * 分页查询用户信息接口
     * @param userPageVo
     * @return
     */
    @PostMapping("/admin/users/page")
    public R getUserPage(@RequestBody @Validated UserPageVo userPageVo) {
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
     * @return  通用返回结果
     */
    @GetMapping("/admin/users/{uuid}")
    public R getBackendUserByUUID(@PathVariable String uuid) {
        User user = this.userService.searchUserByUUID(uuid);
        return R.ok()
                .put("result", user);
    }


    /**
     * 添加/注册新用户接口
     * @param userAddVo
     * @return
     */
    @PostMapping("/admin/users")
    public R addUser(@RequestBody @Validated UserAddVo userAddVo) {
        int role = this.userService.addUser(userAddVo);
        return R.ok()
                .put("role",role);
    }


    /**
     * 更新用户接口
     * @param userUpdateVo
     * @return
     */
    @PutMapping("/admin/users")
    public R updateUser(@RequestBody @Validated UserUpdateVo userUpdateVo) {
        int role = this.userService.updateUser(userUpdateVo);
        return R.ok()
                .put("role",role);
    }






}
