package com.aomsir.jewixapi.utils;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description: 分页工具类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public class PageUtils implements Serializable {

    private static final long serialVersionUID = 1L;

    private  long totalCount;     // 总记录数
    private int pageSize;       // 每页记录数
    private int totalPage;      // 总页数
    private int pageIndex;      // 当前页数
    private List list;          // 列表数据

    public PageUtils(List list, long totalCount, int pageIndex, int pageSize) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }
}
