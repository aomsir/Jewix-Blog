package com.aomsir.jewixapi.handler;


import com.aomsir.jewixapi.utils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

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

public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(SimpleAuthenticationEntryPoint.class);

    public SimpleAuthenticationEntryPoint() {
    }

    @Override
    public void commence(HttpServletRequest req,
                         HttpServletResponse resp,
                         AuthenticationException e) throws IOException, ServletException {
        log.error("SimpleAuthenticationEntryPoint-commence执行了,message:{}",e.getMessage());

        R r;
        if (req.getAttribute("CustomerAuthenticationException") != null) {
            r = R.error("登录凭证已过期,请重新登录");;
        } else {
            r = R.error("访问被拒绝,原因:"+e.getMessage());
        }


        resp.setStatus(HttpStatus.OK.value());
        resp.setContentType("application/json;charset=UTF-8");
        String s = new ObjectMapper().writeValueAsString(r);
        resp.getWriter().println(s);
    }
}
