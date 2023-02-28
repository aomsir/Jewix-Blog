package com.aomsir.jewixapi.pojo.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author: Aomsir
 * @Date: 2023/2/28
 * @Description: 相册实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class Photo {
    private Integer id;
    private Integer type;
    private String location;
    private String url;
    private String fileName;
    private Date createTime;
}
