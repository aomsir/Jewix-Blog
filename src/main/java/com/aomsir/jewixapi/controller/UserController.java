package com.aomsir.jewixapi.controller;

import com.aomsir.jewixapi.pojo.vo.LoginVo;
import com.aomsir.jewixapi.service.UserService;
import com.aomsir.jewixapi.utils.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Aomsir
 * @Date: 2023/2/19
 * @Description: 用户相关控制器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@RestController
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo) {
        String token = this.userService.login(loginVo);
        return R.ok(token);
    }

    @GetMapping("/hello")
    public R hello() {
        return R.ok("hello");
    }
}
