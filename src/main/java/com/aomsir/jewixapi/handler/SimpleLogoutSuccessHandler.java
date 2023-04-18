package com.aomsir.jewixapi.handler;

import com.aomsir.jewixapi.utils.HostHolder;
import com.aomsir.jewixapi.utils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Aomsir
 * @Date: 2023/4/18
 * @Description: 通用退出登录处理器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Component
public class SimpleLogoutSuccessHandler implements LogoutSuccessHandler {
    @Resource
    private HostHolder hostHolder;


    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {
        // 此逻辑是在过滤链的末尾执行的,可以直接查询注销
        R r;
        Long userId = this.hostHolder.getUserId();
        // 避免接口被频繁使用
        if (userId == null || userId < 10000) {
            r = R.error("请先登录");
        } else {
            Boolean deleted = this.redisTemplate.delete("user:token:" + userId);
            r = R.ok("注销成功");
            // if (deleted) {
            //     r = R.ok("注销成功");
            // } else {
                // 小概率事件:Once过滤器后,Redis数据过期
            //     r = R.error("已经注销,不要频繁注销嗷");
            // }

        }


        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        String s = new ObjectMapper().writeValueAsString(r);
        httpServletResponse.getWriter().println(s);
    }
}
