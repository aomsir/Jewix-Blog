package com.aomsir.jewixapi.handler;

import com.aomsir.jewixapi.utils.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description: 授权失败异常处理器
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
public class SimpleAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest req,
                       HttpServletResponse resp,
                       AccessDeniedException e) throws IOException, ServletException {
        R r = R.error("权限不足,无法访问");
        resp.setStatus(HttpStatus.OK.value());

        resp.setContentType("application/json;charset=UTF-8");
        String s = new ObjectMapper().writeValueAsString(r);
        resp.getWriter().println(s);
    }
}
