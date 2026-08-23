package com.aomsir.jewixapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description: OpenAPI配置类(测试环境下可访问)
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Configuration
@Profile("dev")
public class OpenApiConfiguration {
    @Bean
    public GroupedOpenApi webApiConfig() {
        return GroupedOpenApi.builder()
                .group("WebAPI")
                .pathsToMatch("/**")
                .build();
    }

    @Bean
    public OpenAPI webApiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("Jewix博客系统-API文档")
                        .description("Jewix博客系统,新一代博客系统")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Aomsir")
                                .url("https://www.say521.cn/")
                                .email("info@say521.cn")));
    }
}
