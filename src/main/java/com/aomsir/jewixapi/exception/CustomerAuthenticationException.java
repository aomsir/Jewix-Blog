package com.aomsir.jewixapi.exception;

import org.springframework.security.core.AuthenticationException;
/**
 * @Author: Aomsir
 * @Date: 2023/4/15
 * @Description: 自定义认证异常
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public class CustomerAuthenticationException extends AuthenticationException{
    public CustomerAuthenticationException(String message) {
        super(message);
    }


    public CustomerAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }



}
