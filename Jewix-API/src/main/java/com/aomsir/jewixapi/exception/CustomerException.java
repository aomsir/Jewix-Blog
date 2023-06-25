package com.aomsir.jewixapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description: 自定义业务异常
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomerException extends RuntimeException{
    public CustomerException(String message) {
        super(message);
    }
}
