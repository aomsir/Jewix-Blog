package com.aomsir.jewixapi.service.impl;

import cn.hutool.json.JSONUtil;
import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.WebConfigMapper;
import com.aomsir.jewixapi.pojo.entity.WebConfig;
import com.aomsir.jewixapi.pojo.vo.InfoWebConfigAddVo;
import com.aomsir.jewixapi.pojo.vo.InfoWebConfigUpdateVo;
import com.aomsir.jewixapi.service.WebConfigService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    @Transactional
    public int addWebConfig(InfoWebConfigAddVo infoWebConfigAddVo) {

        Integer type = infoWebConfigAddVo.getType();
        if (this.webConfigMapper.queryWebConfigByType(type) != null) {
            throw new CustomerException(CONFIG_TYPE_HAS_EXISTED);
        }

        Date createTime = new Date();
        Date updateTime = new Date();
        String config = JSONUtil.toJsonStr(infoWebConfigAddVo);

        Map<String, Object> param = new HashMap<String, Object>(){{
            put("config",config);
            put("createTime",createTime);
            put("updateTime",updateTime);
            put("type",type);
        }};
        return this.webConfigMapper.insertWebConfig(param);
    }

    @Override
    @Transactional
    public int updateWebConfig(InfoWebConfigUpdateVo infoWebConfigUpdateVo) {

        Integer type = infoWebConfigUpdateVo.getType();
        Integer id = infoWebConfigUpdateVo.getId();

        WebConfig webConfig_1 = this.webConfigMapper.queryWebConfigByType(type);
        WebConfig webConfig_2 = this.webConfigMapper.queryWebConfigById(id);
        if (webConfig_1 == null || webConfig_2 == null) {
            throw new CustomerException(CONFIG_INFO_IS_NULL);
        }

        Date updateTime = new Date();
        String config = JSONUtil.toJsonStr(infoWebConfigUpdateVo);

        Map<String, Object> param = new HashMap<String, Object>(){{
            put("id",id);
            put("config",config);
            put("updateTime",updateTime);
            put("type",type);
        }};

        return this.webConfigMapper.updateWebConfig(param);
    }

    @Override
    public WebConfig searchInfoAllByType(Integer type) {
        WebConfig webConfig = this.webConfigMapper.queryWebConfigByType(type);
        if (webConfig == null) {
            throw new CustomerException(CONFIG_INFO_IS_NULL);
        }
        return webConfig;
    }
}
