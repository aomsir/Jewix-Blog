package com.aomsir.jewixapi.config;

import com.aomsir.jewixapi.handler.EmailPasswordAuthenticationFilter;
import com.aomsir.jewixapi.handler.PerTokenVerifyFilter;
import com.aomsir.jewixapi.handler.SimpleAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
/**
 * @Author: Aomsir
 * @Date: 2023/2/19
 * @Description: 新版SpringSecurity配置类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

// TODO:更换SpringBoot2.7.5,重写配置
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    @Resource
    private PerTokenVerifyFilter tokenVerifyFilter;

    @Bean
    public PasswordEncoder BcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



    /**
     * 采用自定义数据源进行认证
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(BcryptPasswordEncoder());
    }


    /**
     * 暴露AuthenticationManager
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    // TODO:添加退出登录成功回调handler
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/admin/**").authenticated()   // /admin请求都过SpringSecurity且需要认证
                .and().csrf().disable()  // 关闭csrf
                .cors()                 // 开启跨域以便前端调用接口
                .and().exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)    // 认证异常捕获
                .accessDeniedHandler(this.accessDeniedHandler);             // 失败异常捕获

        http.addFilterBefore(this.tokenVerifyFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new EmailPasswordAuthenticationFilter(authenticationManager()), PerTokenVerifyFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);   // 禁用Session
    }
}
