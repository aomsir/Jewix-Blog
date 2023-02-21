package com.aomsir.jewixapi.exception;

import com.aomsir.jewixapi.utils.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description: 全局业务异常处理器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@ControllerAdvice(annotations = RestController.class)
@ResponseBody
public class GlobalExceptionHandler {

    /**
     * 全局异常处理器
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        return R.error(e.getMessage());
    }


    /**
     * 自定义业务异常捕获
     * @param ex
     * @return
     */
    @ExceptionHandler(CustomerException.class)
    public R exceptionHandler(CustomerException ex) {
        return R.error(ex.getMessage());
    }
}
