package com.aomsir.jewixapi.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.bitwalker.useragentutils.UserAgent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

    @Value("${jewix.tencent-location-api-key}")
    private String tencentApiKey;


    /**
     * 获取用户真实IP
     * @param request request请求对象
     * @return 用户真实ip
     */
    public String getRealIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按','分割
        if (ip != null && ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }
        return ip;
    }



    /**
     * 获取用户城市所在地
     * @param ip ip
     * @return 所在地
     * @throws JsonProcessingException 异常
     */
    public String getLocationInfo(String ip) throws JsonProcessingException {
        String finalLocation = "未知";
        if (this.isLocalIp(ip)) {
            return finalLocation;
        } else if ("".equals(ip)) {
            return finalLocation;
        }

        String url = "https://apis.map.qq.com/ws/location/v1/ip";
        String key = this.tencentApiKey;
        url = String.format("%s?ip=%s&key=%s", url, ip,key);
        String response =  this.restTemplate.getForObject(url, String.class);

        JsonNode parentNode = this.objectMapper.readTree(response);
        JsonNode sonNode = parentNode.get("result").get("ad_info");

        String nation = sonNode.get("nation").asText();
        String province = sonNode.get("province").asText();
        String city = sonNode.get("city").asText();


        // 处理内容
        if (StringUtils.isEmpty(nation) || StringUtils.isBlank(nation)) {
            if (StringUtils.isEmpty(province) || StringUtils.isBlank(province)) {
                if (StringUtils.isEmpty(city)  || StringUtils.isBlank(city)) {
                    // 未知
                    finalLocation = "未知";
                }
            } else {
                finalLocation = province;
                if (!StringUtils.isEmpty(city)  || !StringUtils.isBlank(city)) {
                    if (!province.equals(city)) {
                        // 湖北省-武汉市
                        finalLocation = finalLocation + "-" + city;
                    }
                }
            }
        } else {
            if (!StringUtils.isEmpty(province) || !StringUtils.isBlank(province)) {
                finalLocation = province;
                if (!StringUtils.isEmpty(city)  || !StringUtils.isBlank(city)) {
                    // 以免出现 上海市-上海市
                    if (!province.equals(city)) {
                        // 湖北省-武汉市
                        finalLocation = finalLocation + "-" + city;
                    }
                }
            } else {
                if (StringUtils.isEmpty(city)  || StringUtils.isBlank(city)) {
                    // 新加坡
                    finalLocation = nation;
                }
            }
        }

        return finalLocation;
    }

    /**
     * 解析用户浏览器代理
     * @param userAgent 用户代理
     * @return 代理解析数据
     */
    public Map<String, String> parseUserAgent(String userAgent) {
        UserAgent agent = UserAgent.parseUserAgentString(userAgent);
        Map<String, String> map = new HashMap<>();
        map.put("browser", agent.getBrowser().getName());
        map.put("os", agent.getOperatingSystem().getName());
        return map;
    }

    /**
     * 查询是否是本地IP
     * @param ip 查询IP
     * @return 是否是本地
     */
    private boolean isLocalIp(String ip) {
        if (ip == null || ip.isEmpty()) {
            return false;
        }
        try {
            InetAddress address = InetAddress.getByName(ip);
            return address.isSiteLocalAddress() || address.isLoopbackAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return false;
        }
    }

}
