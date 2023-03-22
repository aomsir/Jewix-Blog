package com.aomsir.jewixapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Aomsir
 * @Date: 2023/3/21
 * @Description: 网络、UA工具类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Component
public class NetUtils {

    @Resource
    private ObjectMapper objectMapper;


    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${jewix.gaode-api-key}")
    private String gaodeApiKey;


    /**
     * 获取用户真实IP
     * @param request
     * @return
     */
    public String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }


    /**
     * 获取用户城市所在地
     * @param ip
     * @return
     * @throws JsonProcessingException
     */
    public Map<String, String > getLocationInfo(String ip) throws JsonProcessingException {
        String url = "https://restapi.amap.com/v3/ip";
        String key = this.gaodeApiKey;
        url = String.format("%s?ip=%s&key=%s", url, ip,key);
        String response =  restTemplate.getForObject(url, String.class);

        // TODO:解析json
        JsonNode locationResponse = objectMapper.readTree(response);
        String province = locationResponse.get("province").asText();
        String city = locationResponse.get("city").asText();

        if (StringUtils.isBlank(province)) {
            province = "未知";
        }
        if (StringUtils.isBlank(city)) {
            city = "未知";
        }

        String finalProvince = province;
        String finalCity = city;
        Map<String, String> map = new HashMap<String, String>(){{
            put("province", finalProvince);
            put("city", finalCity);
        }};;
        return map;
    }

    public Map<String, String> parseUserAgent(String userAgent) {
        UserAgent agent = UserAgent.parseUserAgentString(userAgent);
        Map<String, String> map = new HashMap<>();
        map.put("browser", agent.getBrowser().getName());
        map.put("os", agent.getOperatingSystem().getName());
        return map;
    }

}
