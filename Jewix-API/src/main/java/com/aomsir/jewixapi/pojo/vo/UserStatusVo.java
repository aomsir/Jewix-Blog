package com.aomsir.jewixapi.pojo.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @Author: Aomsir
 * @Date: 2023/3/7
 * @Description: 更新用户状态VO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class UserStatusVo implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 用户uuid
     */
    @NotNull(message = "uuid不能为空")
    private String uuid;

    /**
     * 状态
     */
    @NotNull(message = "status不能为空")
    @Range(min = 1,max = 2,message = "status应在1-2之间")
    private Integer status;
}
