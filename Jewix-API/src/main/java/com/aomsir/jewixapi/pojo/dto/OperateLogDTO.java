package com.aomsir.jewixapi.pojo.dto;

import com.aomsir.jewixapi.pojo.entity.OperatedLog;
import lombok.Data;

/**
 * @Author: Aomsir
 * @Date: 2023/6/14
 * @Description: 操作日志DTO实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class OperateLogDTO extends OperatedLog {

    /**
     * 用户昵称
     */
    private String nickname;
}
