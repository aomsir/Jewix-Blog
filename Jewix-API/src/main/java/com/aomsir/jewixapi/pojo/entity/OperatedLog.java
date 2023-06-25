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

    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 操作者ip
     */
    private String ip;

    /**
     * 操作者位置
     */
    private String location;

    /**
     * 操作模块
     */
    private String optModule;

    /**
     * 操作类型
     */
    private String optType;

    /**
     * 请求方法 - GET/POST/PUT/DELETE
     */
    private String reqMethod;

    /**
     * 执行的Java方法
     */
    private String javaMethod;

    /**
     * 请求的Restful路径
     */
    private String reqUrl;

    /**
     * 请求的消息
     */
    private String optReqMsg;

    /**
     * 响应消息
     */
    private String optRespMsg;

    /**
     * 操作时间
     */
    private Date optTime;
}
