package com.aomsir.jewixapi.exception;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.aomsir.jewixapi.utils.R;
import org.apache.ibatis.exceptions.IbatisException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static com.aomsir.jewixapi.constants.ExceptionConstants.DATABASE_EXCEPTION;

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

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 全局异常处理器
     * @param e 异常对象
     * @return 响应数据
     */
    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        return R.error(e.getMessage());
    }

    /**
     * 自定义业务异常捕获
     * @param ex 异常对象
     * @return 响应数据
     */
    @ExceptionHandler(CustomerException.class)
    public R exceptionHandler(CustomerException ex) {
        return R.error(ex.getMessage());
    }


    /**
     * 授权异常处理器(已作废)
     * @param e 异常对象
     * @return 响应数据
     */
    @ExceptionHandler(CustomerAuthenticationException.class)
    public R authenticationException(CustomerAuthenticationException e) {
        return R.error(e.getMessage());
    }


    /**
     * JSON参数校验异常处理器
     * @param e 异常对象
     * @return 响应数据
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R jsonParamsException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<Object> errorList = CollectionUtil.newArrayList();

        log.error("jsonParamsException is invoke...");
        e.printStackTrace();

        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String msg = String.format("%s%s；", fieldError.getField(), fieldError.getDefaultMessage());
            errorList.add(msg);
        }
        return R.error(500,errorList.get(0).toString());
    }


    /**
     * 单个参数异常处理器
     * @param e 异常对象
     * @return 响应数据
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public R ParamsException(ConstraintViolationException e) {
        log.error("ParamsException is invoke...");
        e.printStackTrace();

        List<Object> errorList = CollectionUtil.newArrayList();
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
     * 数据库类型异常
     * @param e 异常对象
     * @return 响应数据
     */
    @ExceptionHandler({SQLException.class,
            IbatisException.class,})
    public R SQLException(Exception e) {
        log.error("{}", e.getMessage());
        return R.error(DATABASE_EXCEPTION);
    }

}
