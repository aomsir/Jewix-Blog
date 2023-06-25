package com.aomsir.jewixapi.config;

import com.upyun.RestManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Aomsir
 * @Date: 2023/2/27
 * @Description: 云存储相关配置类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Configuration
public class UpyunConfiguration {

    @Value("${cloud.upyun.bucket}")
    private String bucket;

    @Value("${cloud.upyun.operator-name}")
    private String operatorName;

    @Value("${cloud.upyun.operator-pwd}")
    private String operatorPwd;

    @Bean
    public RestManager restManager() {
        RestManager restManager = new RestManager(this.bucket,this.operatorName,this.operatorPwd);

        // 自定选择最优接入点
        restManager.setApiDomain(RestManager.ED_AUTO);
        return restManager;
    }
}
