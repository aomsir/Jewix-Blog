package com.aomsir.jewixapi.handler;

import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.utils.HostHolder;
import com.aomsir.jewixapi.utils.JwtUtils;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
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
import java.util.HashSet;

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
    private HostHolder hostHolder;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals("/login")) {
            filterChain.doFilter(request,response);
            return;
        }


        // TODO: 完善校验功能

        SecurityContext securityContext = SecurityContextHolder.getContext();
        String token = request.getHeader("token");

        // 没有携带token则进入后续的Filter,后续的Filter会验证当前程序能否正常行走
        if (token == null) {
            securityContext.setAuthentication(null);
            filterChain.doFilter(request,response);
            return;
        }

        // 验证token能否验证
        try {
            JwtUtils.verify(token);
        } catch (Exception e) {
            throw new CustomerException("token过期");
        }

        DecodedJWT jwt = JwtUtils.getToken(token);
        String userId = jwt.getClaim("userId").asString();
        this.hostHolder.setUserId(Long.valueOf(userId));

        // 通过id在Redis中查询User信息进行封装
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("info@say521.cn",null,null));
        filterChain.doFilter(request, response);
    }
}
