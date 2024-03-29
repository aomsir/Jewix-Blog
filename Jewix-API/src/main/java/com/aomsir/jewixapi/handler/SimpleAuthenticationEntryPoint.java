package com.aomsir.jewixapi.handler;


import com.aomsir.jewixapi.util.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.aomsir.jewixapi.constant.CommonConstants.TICKET_ERROR;

/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description: 认证失败异常处理器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Component
public class SimpleAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger log = LoggerFactory.getLogger(SimpleAuthenticationEntryPoint.class);


    @Override
    public void commence(HttpServletRequest req,
                         HttpServletResponse resp,
                         AuthenticationException e) throws IOException, ServletException {
        log.error("SimpleAuthenticationEntryPoint-commence执行了,message:{}",e.getMessage());

        R r;
        if (req.getAttribute("CustomerAuthenticationException") != null) {
            r = R.error(401,TICKET_ERROR);
        } else {
            e.printStackTrace();
            r = R.error(401,"访问被拒绝,请先登录");
        }


        resp.setStatus(HttpStatus.OK.value());
        resp.setContentType("application/json;charset=UTF-8");
        String s = new ObjectMapper().writeValueAsString(r);
        resp.getWriter().println(s);
    }
}
