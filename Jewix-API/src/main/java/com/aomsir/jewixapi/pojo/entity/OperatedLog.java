package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Aomsir
 * @Date: 2023/6/14
 * @Description: 操作日志实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Data
public class OperatedLog implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long userId;
    private String ip;
    private String location;
    private String optModule;
    private String optType;
    private String reqMethod;
    private String javaMethod;
    private String reqUrl;
    private String optReqMsg;
    private String optRespMsg;
    private Date optTime;
}
