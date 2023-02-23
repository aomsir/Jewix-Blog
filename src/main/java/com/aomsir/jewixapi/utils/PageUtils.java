package com.aomsir.jewixapi.utils;

import lombok.Data;

import java.util.List;

/**
 * @Author: Aomsir
 * @Date: 2023/2/23
 * @Description: 分页封装工具类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Data
public class PageUtils {

    private static final long serialVersionUID = 1L;
    /**
     * 总记录数
     */
    private long totalCount;
    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 总页数
     */
    private int totalPage;
    /**
     * 当前页数
     */
    private int pageIndex;
    /**
     * 列表数据
     */
    private List list;

    public PageUtils(List list, long totalCount, int pageIndex, int pageSize) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.pageIndex = pageIndex;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

}
