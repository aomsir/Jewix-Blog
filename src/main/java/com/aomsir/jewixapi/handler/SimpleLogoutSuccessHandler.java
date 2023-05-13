package com.aomsir.jewixapi.handler;

import com.aomsir.jewixapi.utils.JwtUtils;
import com.aomsir.jewixapi.utils.R;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.aomsir.jewixapi.constants.RedisConstants.USER_INFO_KEY;
import static com.aomsir.jewixapi.constants.RedisConstants.USER_TOKEN_KEY;
import static com.aomsir.jewixapi.constants.SecurityConstants.LOGOUT_SUCCESS;
import static com.aomsir.jewixapi.constants.SecurityConstants.PLEASE_LOGIN_FIRST;

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
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Authentication authentication) throws IOException, ServletException {
        // 此逻辑是在过滤链的末尾执行的,但不会走OncePerRequestFilter
        R r;
        String token = httpServletRequest.getHeader("token");
        if (token == null) {
            r = R.error(PLEASE_LOGIN_FIRST);
        } else {
            try {
                JwtUtils.verify(token);
            } catch (Exception e) {
                r = R.ok(LOGOUT_SUCCESS);
            }

            // 解析token中的内容
            DecodedJWT jwt = JwtUtils.getToken(token);
            String userId = jwt.getClaim("userId").asString();
            // 避免接口被频繁使用
            if (userId == null || Long.parseLong(userId) < 10000) {
                r = R.error(PLEASE_LOGIN_FIRST);
            } else {
                String tokenInRedis = (String) this.redisTemplate.opsForValue()
                        .get(USER_TOKEN_KEY + userId);
                if (tokenInRedis == null) {
                    r = R.ok(LOGOUT_SUCCESS);
                } else {
                    this.redisTemplate.delete(USER_TOKEN_KEY + userId);
                    this.redisTemplate.delete(USER_INFO_KEY + userId);
                    r = R.ok(LOGOUT_SUCCESS);
                }
            }
        }

        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        String s = new ObjectMapper().writeValueAsString(r);
        httpServletResponse.getWriter().println(s);
    }
}
