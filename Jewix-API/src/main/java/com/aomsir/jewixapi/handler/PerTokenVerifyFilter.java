package com.aomsir.jewixapi.handler;

import com.aomsir.jewixapi.util.UserHolder;
import com.aomsir.jewixapi.util.JwtUtils;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static com.aomsir.jewixapi.constant.CommonConstants.TICKET_ERROR;
import static com.aomsir.jewixapi.constant.RedisConstants.USER_TOKEN_KEY;

/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description: token验证器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Component
public class PerTokenVerifyFilter extends OncePerRequestFilter {

    @Resource
    private UserHolder userHolder;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Value("${server.servlet.context-path}")
    private String prefix;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(this.prefix + "/login")) {
            filterChain.doFilter(request,response);
            return;
        }


        // TODO：从Redis中获取authentication并存储
        SecurityContext securityContext = SecurityContextHolder.getContext();

        String token = request.getHeader("token");

        // 没有携带token则进入后续的Filter,后续的Filter会验证当前程序能否正常行走
        if (token == null) {
            request.setAttribute("CustomerAuthenticationException",TICKET_ERROR);
            filterChain.doFilter(request,response);
            return;
        }

        // token能否正确
        try {
            JwtUtils.verify(token);
        } catch (Exception e) {
            request.setAttribute("CustomerAuthenticationException",TICKET_ERROR);
            filterChain.doFilter(request,response);
            return;
        }

        // 解析token中的内容
        DecodedJWT jwt = JwtUtils.getToken(token);
        String userId = jwt.getClaim("userId").asString();

        String tokenInRedis = (String) this.redisTemplate.opsForValue()
                .get(USER_TOKEN_KEY + userId);

        if (Objects.isNull(tokenInRedis) || tokenInRedis.isEmpty()) {
            request.setAttribute("CustomerAuthenticationException",TICKET_ERROR);
            filterChain.doFilter(request,response);
            return;
        }
         else if (!tokenInRedis.equals(token)) {
             request.setAttribute("CustomerAuthenticationException",TICKET_ERROR);
             filterChain.doFilter(request,response);
             return;
         }

        this.userHolder.setUserId(Long.valueOf(userId));

        // TODO:通过Redis查询封装的用户信息等
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("info@say521.cn",null,null));
        filterChain.doFilter(request, response);
    }
}
