package com.aomsir.jewixapi.handler;

import com.aomsir.jewixapi.util.NetUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Aomsir
 * @Date: 2023/4/23
 * @Description: 流量控制过滤器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
// TODO: 进行整合测试
@Deprecated
public class ReteLimitFilter extends OncePerRequestFilter {

    @Value("${server.servlet.context-path}")
    private String prefix;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Resource
    private NetUtils netUtils;

    private Integer timeout;
    private Long limit = 1L;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(this.prefix + "/login")) {
            filterChain.doFilter(request,response);
            return;
        }

        String ip = this.netUtils.getRealIp(request);
        Long count = (Long) this.redisTemplate.opsForValue().get("ip:limit:" + ip);
        if (count == null) {
            count = 1L;
            this.redisTemplate.opsForValue().set("ip:limit:" + ip,count,1, TimeUnit.MINUTES);
        } else {
            if (count > limit) {
                request.setAttribute("CustomerAuthenticationException","频繁访问,请等待1分钟再进行访问");
                filterChain.doFilter(request,response);
                return;
            } else {
                count++;
                this.redisTemplate.opsForValue().increment("ip:limit:" + ip);
            }

        }
    }
}
