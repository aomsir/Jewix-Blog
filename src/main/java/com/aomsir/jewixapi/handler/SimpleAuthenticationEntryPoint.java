package com.aomsir.jewixapi.handler;

import com.aomsir.jewixapi.utils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description: 认证失败异常处理器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Component
public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest req,
                         HttpServletResponse resp,
                         AuthenticationException e) throws IOException, ServletException {
        R r = R.error("认证失败,请重新登录");
        resp.setStatus(HttpStatus.OK.value());

        resp.setContentType("application/json;charset=UTF-8");
        String s = new ObjectMapper().writeValueAsString(r);
        resp.getWriter().println(s);
    }
}
