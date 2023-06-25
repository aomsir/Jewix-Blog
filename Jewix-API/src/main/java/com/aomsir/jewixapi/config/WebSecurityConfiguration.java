package com.aomsir.jewixapi.config;

import com.aomsir.jewixapi.handler.*;
import com.aomsir.jewixapi.service.LogService;
import com.aomsir.jewixapi.service.MenuService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * @Author: Aomsir
 * @Date: 2023/2/19
 * @Description: 新版SpringSecurity配置类
 * @TODO 更换SpringBoot2.7.5,重写配置
 * @TODO 解决AccessDeniedHandler
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

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


    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    @Resource
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Resource
    private LogService logService;

    @Resource
    private MenuService menuService;


    @Bean
    public PasswordEncoder bcryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }



    /**
     * 采用自定义数据源进行认证
     * @param auth 认证对象
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(bcryptPasswordEncoder());
    }


    /**
     * 暴露AuthenticationManager
     */
    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(req -> req.mvcMatchers("/admin/**").authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(configurationSource()))
                .logout(out -> out
                        .logoutUrl("/logout")
                        .logoutSuccessHandler(this.simpleLogoutSuccessHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        http.addFilterBefore(this.tokenVerifyFilter, FilterSecurityInterceptor.class);
        http.addFilterAfter(new EmailPasswordAuthenticationFilter(authenticationManager(),redisTemplate,logService,menuService),
                PerTokenVerifyFilter.class);

        http.exceptionHandling(ex ->
                ex.authenticationEntryPoint(this.authenticationEntryPoint)
                        .accessDeniedHandler(this.accessDeniedHandler));
    }


    /**
     * SpringSecurity跨域配置
     */
    CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
