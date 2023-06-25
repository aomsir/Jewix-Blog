package com.aomsir.jewixapi.annotation;

import java.lang.annotation.*;

/**
 * @Author: Aomsir
 * @Date: 2023/4/15
 * @Description: 操作日志注解
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLog {
    /**
     * @return 操作类型
     */
    String optType() default "";
}

