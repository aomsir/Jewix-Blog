package com.aomsir.jewixapi.utils;

import cn.hutool.crypto.SecureUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @Author: Aomsir
 * @Date: 2023/2/19
 * @Description: SpringSecurity加密解密组件
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Component
public class BlogPasswordEncoder implements PasswordEncoder {

    @Value("${blog.security.salt}")
    private String salt;


    @Override
    public String encode(CharSequence rawPassword) {
        return SecureUtil.sha1(this.salt + rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        String encodePassword1 = this.encode(rawPassword);
        return encodePassword1.equals(encodedPassword);
    }
}
