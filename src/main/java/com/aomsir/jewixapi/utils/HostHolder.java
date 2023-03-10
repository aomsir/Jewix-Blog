package com.aomsir.jewixapi.utils;

import org.springframework.stereotype.Component;

/**
 * @Author: Aomsir
 * @Date: 2023/3/9
 * @Description: ThreadLocal工具类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Component
public class HostHolder {
    private final ThreadLocal<Long> users = new ThreadLocal<>();

    public void setUserId(Long id) {
        users.set(id);
    }

    public Long getUserId() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }
}
