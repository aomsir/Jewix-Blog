package com.aomsir.jewixapi.exception;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.aomsir.jewixapi.utils.R;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;

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
     * 处理 json 请求体调用接口对象参数校验失败抛出的异常
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R jsonParamsException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List errorList = CollectionUtil.newArrayList();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String msg = String.format("%s%s；", fieldError.getField(), fieldError.getDefaultMessage());
            errorList.add(msg);
        }
        return R.error(500,errorList.get(0).toString());
    }


    /**
     * 处理单个参数校验失败抛出的异常
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R ParamsException(ConstraintViolationException e) {

        List errorList = CollectionUtil.newArrayList();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            StringBuilder message = new StringBuilder();
            Path path = violation.getPropertyPath();
            String[] pathArr = StrUtil.splitToArray(path.toString(), ".");
            String msg = message.append(pathArr[1]).append(violation.getMessage()).toString();
            errorList.add(msg);
        }
        return R.error(500, errorList.get(0).toString());
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
