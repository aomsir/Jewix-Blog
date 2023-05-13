package com.aomsir.jewixapi.utils;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @Author: Aomsir
 * @Date: 2023/3/9
 * @Description: ThreadLocal工具类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UserHolder {
    private static final ThreadLocal<Long> users = new ThreadLocal<>();

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
