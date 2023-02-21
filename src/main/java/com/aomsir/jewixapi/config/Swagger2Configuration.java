package com.aomsir.jewixapi.config;

import org.springframework.context.annotation.Configuration;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description:
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Configuration
@EnableSwagger2
@Profile("dev")      // 测试环境下可访问
public class Swagger2Configuration {
    @Bean
    public Docket webApiConfig(){

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.and(PathSelectors.regex("/.*")))
                .build();
    }

    private ApiInfo webApiInfo() {

        return new ApiInfoBuilder()
                .title("Jewix博客系统-API文档")
                .description("Jewix博客系统,新一代博客系统")
                .version("1.0")
                .contact(new Contact("Aomsir", "https://www.say521.cn/", "info@say521.cn"))
                .build();
    }
}
