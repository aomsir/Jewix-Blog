package com.aomsir.jewixapi.config;

import com.aomsir.jewixapi.handler.*;
import com.aomsir.jewixapi.utils.HostHolder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Arrays;

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
    private PerTokenVerifyFilter tokenVerifyFilter;

    @Resource
    private SimpleLogoutSuccessHandler simpleLogoutSuccessHandler;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Value("${server.servlet.context-path}")
    private String prefix;

    @Bean
    public PasswordEncoder BcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



    /**
     * 采用自定义数据源进行认证
     * @param auth 认证对象
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(BcryptPasswordEncoder());
    }


    /**
     * 暴露AuthenticationManager
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
                .and().exceptionHandling()     // 开启异常处理
                    .authenticationEntryPoint(new SimpleAuthenticationEntryPoint())    // 认证异常捕获
                    .accessDeniedHandler(new SimpleAccessDeniedHandler())           // 失败异常捕获
                .and().csrf().disable()  // 关闭csrf
                    .cors().configurationSource(configurationSource())  // 开启跨域以便前端调用接口
                .and().logout()
                        .logoutUrl("/logout")
                                .logoutSuccessHandler(this.simpleLogoutSuccessHandler);

        http.addFilterBefore(this.tokenVerifyFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterAfter(new EmailPasswordAuthenticationFilter(authenticationManager(),redisTemplate),
                PerTokenVerifyFilter.class);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);   // 禁用Session
    }


    /**
     * SpringSecurity跨域配置
     */
    CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));
        corsConfiguration.setAllowedMethods(Arrays.asList("*"));
        corsConfiguration.setAllowedOrigins(Arrays.asList("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
