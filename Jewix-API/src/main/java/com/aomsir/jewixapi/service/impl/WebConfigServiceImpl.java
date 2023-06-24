package com.aomsir.jewixapi.service.impl;

import cn.hutool.json.JSONUtil;
import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.WebConfigMapper;
import com.aomsir.jewixapi.pojo.entity.WebConfig;
import com.aomsir.jewixapi.pojo.vo.InfoWebConfigAddVo;
import com.aomsir.jewixapi.pojo.vo.InfoWebConfigUpdateVo;
import com.aomsir.jewixapi.service.WebConfigService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import static com.aomsir.jewixapi.constant.RedisConstants.WEB_CONFIG_KEY;
import static com.aomsir.jewixapi.constant.WebConfigConstants.CONFIG_INFO_IS_NULL;
import static com.aomsir.jewixapi.constant.WebConfigConstants.CONFIG_TYPE_HAS_EXISTED;

/**
 * @Author: Aomsir
 * @Date: 2023/3/7
 * @Description: 网站信息设置业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Service
public class WebConfigServiceImpl implements WebConfigService {

    @Resource
    private WebConfigMapper webConfigMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional
    public int addWebConfig(InfoWebConfigAddVo infoWebConfigAddVo) {
        HashMap<String, String> socialInfo = infoWebConfigAddVo.getSocialInfo();
        if (socialInfo.size() > 3) {
            throw new CustomerException("最多只允许3个社交信息嗷");
        }

        String regex = "(?i)^(http|https)://.*$";
        Pattern pattern = Pattern.compile(regex);
        Set<String> keySets = socialInfo.keySet();
        for (String keySet : keySets) {
            String url = socialInfo.get(keySet);
            if (!pattern.matcher(url).matches()) {
                throw new CustomerException("社交信息必须是链接");
            }
        }


        Date createTime = new Date();
        Date updateTime = new Date();
        String config = JSONUtil.toJsonStr(infoWebConfigAddVo);

        Map<String, Object> param = new HashMap<String, Object>(){{
            put("config",config);
            put("createTime",createTime);
            put("updateTime",updateTime);

        }};
        return this.webConfigMapper.insertWebConfig(param);
    }

    @Override
    @Transactional
    public int updateWebConfig(InfoWebConfigUpdateVo infoWebConfigUpdateVo) {
        HashMap<String, String> socialInfo = infoWebConfigUpdateVo.getSocialInfo();
        if (socialInfo.size() > 3) {
            throw new CustomerException("最多只允许3个社交信息嗷");
        }

        // 判断url是否正确
        String regex = "(?i)^(http|https)://.*$";
        Pattern pattern = Pattern.compile(regex);
        Set<String> keySets = socialInfo.keySet();
        for (String keySet : keySets) {
            String url = socialInfo.get(keySet);
            if (!pattern.matcher(url).matches()) {
                throw new CustomerException("社交信息必须是链接");
            }
        }

        // 查询网站设置信息
        Integer id = infoWebConfigUpdateVo.getId();
        WebConfig webConfig_1 = this.webConfigMapper.queryWebConfigById(id);
        if (webConfig_1 == null) {
            throw new CustomerException(CONFIG_INFO_IS_NULL);
        }

        Date updateTime = new Date();
        String configInfo = JSONUtil.toJsonStr(infoWebConfigUpdateVo);

        Map<String, Object> param = new HashMap<String, Object>(){{
            put("id",id);
            put("config",configInfo);
            put("updateTime",updateTime);
        }};

        this.redisTemplate.delete(WEB_CONFIG_KEY);
        return this.webConfigMapper.updateWebConfig(param);
    }

    @Override
    public WebConfig searchWebInfo() {
        WebConfig webConfig = this.webConfigMapper.queryWebConfigInfo();
        if (webConfig == null) {
            throw new CustomerException(CONFIG_INFO_IS_NULL);
        }
        return webConfig;
    }
}
