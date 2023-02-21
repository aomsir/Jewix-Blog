package com.aomsir.jewixapi.exception;

import com.aomsir.jewixapi.utils.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description:
 * @Email: info@say521.cn
 * @GitHub: https://github.com/aomsir
 */
public class GlobalExceptionHandler {

    // 全局异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e) {
        return R.error(e.getMessage());
    }

    // 自定义异常
    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public R error(AuthException e) {
        return R.error(e.getMessage());
    }

    /**
     * spring security异常
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public R error(AccessDeniedException e) throws AccessDeniedException {
        return R.error(e.getMessage());
    }
}
