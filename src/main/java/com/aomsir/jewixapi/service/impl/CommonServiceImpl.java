package com.aomsir.jewixapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.mapper.*;
import com.aomsir.jewixapi.pojo.dto.ArticleArchiveInfoDTO;
import com.aomsir.jewixapi.pojo.dto.WebInfoDTO;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.service.CommonService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.aomsir.jewixapi.constants.RedisConstants.WEB_CONFIG_EXPIRE;
import static com.aomsir.jewixapi.constants.RedisConstants.WEB_CONFIG_KEY;

/**
 * @Author: Aomsir
 * @Date: 2023/4/28
 * @Description: 通用业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
@Service
public class CommonServiceImpl implements CommonService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private WebConfigMapper webConfigMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public WebInfoDTO searchWebInfo() {
        // 缓存命中
        // TODO：相关信息更新时得删缓存
        Map<Object, Object> configMap = this.redisTemplate.opsForHash()
                .entries(WEB_CONFIG_KEY);
        if (configMap.size() > 0) {
            return BeanUtil.toBean(configMap, WebInfoDTO.class);
        }

        User user = this.userMapper.queryUserById(10000L);
        String nickname = user.getNickname();
        String email = user.getEmail();
        String description = user.getDescription();

        Integer articleCount = this.articleMapper.queryArticleCount();
        Integer commentCount = this.commentMapper.queryCommentCount();
        Date lastActive = this.articleMapper.queryLastActive();

        // 将Date对象转换成LocalDate对象
        LocalDate date = new java.sql.Date(lastActive.getTime()).toLocalDate();

        // 获取今天的LocalDate对象
        LocalDate today = LocalDate.now();

        // 计算差值
        long daysBetween = ChronoUnit.DAYS.between(date, today);
        WebInfoDTO webInfoDTO = new WebInfoDTO();
        webInfoDTO.setNickname(nickname);
        webInfoDTO.setEmail(email);
        webInfoDTO.setDescription(description);
        webInfoDTO.setArticleCount(articleCount);
        webInfoDTO.setCommentCount(commentCount);
        webInfoDTO.setLastActive(Math.toIntExact(daysBetween));

        // 存入Redis
        this.redisTemplate.opsForHash()
                .putAll(WEB_CONFIG_KEY, BeanUtil.beanToMap(webInfoDTO));
        this.redisTemplate.expire(WEB_CONFIG_KEY, WEB_CONFIG_EXPIRE, TimeUnit.DAYS);
        return webInfoDTO;
    }

    @Override
    public ArticleArchiveInfoDTO searchArticleArchiveInfo() {
        Integer articleCount = this.articleMapper.queryArticleCountByArchive();
        Integer commentCount = this.commentMapper.queryCommentCountByArchive();
        Integer categoryCount = this.categoryMapper.queryCategoryCount();
        Integer tagCount = Math.toIntExact(this.tagMapper.queryTagCount());

        ArticleArchiveInfoDTO articleArchiveInfoDTO = new ArticleArchiveInfoDTO();
        articleArchiveInfoDTO.setArticleCount(articleCount);
        articleArchiveInfoDTO.setCategoryCount(categoryCount);
        articleArchiveInfoDTO.setCommentCount(commentCount);
        articleArchiveInfoDTO.setTagCount(tagCount);
        return articleArchiveInfoDTO;
    }
}
