package com.aomsir.jewixapi.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperateLog {
    /**
     * @return 操作类型
     */
    String optType() default "";
}

