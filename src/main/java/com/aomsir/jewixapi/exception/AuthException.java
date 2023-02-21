package com.aomsir.jewixapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description:
 * @Email: info@say521.cn
 * @GitHub: https://github.com/aomsir
 */
@Data
@AllArgsConstructor
public class AuthException extends RuntimeException{
    private Integer code;
    private String message;

}
