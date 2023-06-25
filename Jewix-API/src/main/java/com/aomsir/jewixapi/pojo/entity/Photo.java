package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: Aomsir
 * @Date: 2023/2/28
 * @Description: 相册实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class Photo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 照片类型
     */
    private Integer type;

    /**
     * 位置 - 又拍云/阿里云/腾讯云
     */
    private String location;

    /**
     * 相册路径
     */
    private String url;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 创建时间
     */
    private Date createTime;
}
