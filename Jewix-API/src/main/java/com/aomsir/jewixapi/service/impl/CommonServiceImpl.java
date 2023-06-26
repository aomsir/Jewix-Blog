package com.aomsir.jewixapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.aomsir.jewixapi.mapper.*;
import com.aomsir.jewixapi.pojo.dto.ArticleArchiveInfoDTO;
import com.aomsir.jewixapi.pojo.dto.WebInfoDTO;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.pojo.entity.WebConfig;
import com.aomsir.jewixapi.pojo.entity.WebInfo;
import com.aomsir.jewixapi.service.CommonService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.aomsir.jewixapi.constant.RedisConstants.WEB_CONFIG_EXPIRE;
import static com.aomsir.jewixapi.constant.RedisConstants.WEB_CONFIG_KEY;

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
        Map<Object, Object> configMap = this.redisTemplate.opsForHash()
                .entries(WEB_CONFIG_KEY);
        if (!configMap.isEmpty()) {
            return BeanUtil.toBean(configMap, WebInfoDTO.class);
        }

        // 查询站长信息
        User user = this.userMapper.queryUserById(10000L);
        String nickname = user.getNickname();
        String email = user.getEmail();
        String description = user.getDescription();


        // 查询文章数、评论数、最后活跃日期
        Integer articleCount = this.articleMapper.queryArticleCount();
        Integer commentCount = this.commentMapper.queryCommentCount();
        Date lastActive = this.articleMapper.queryLastActive();

        // 将Date对象转换成LocalDate对象
        LocalDate date = new java.sql.Date(lastActive.getTime()).toLocalDate();

        // 获取今天的LocalDate对象、计算差值
        LocalDate today = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(date, today);


        // 计算运行天数
        WebConfig webConfig = this.webConfigMapper.queryWebConfigInfo();
        String configJson = webConfig.getConfig();
        WebInfo webInfo = JSONUtil.toBean(configJson, WebInfo.class);
        Date buildDate = webInfo.getBuildDate();
        LocalDate date1 = new java.sql.Date(buildDate.getTime()).toLocalDate();
        long runTime = ChronoUnit.DAYS.between(date1, today);


        WebInfoDTO webInfoDTO = new WebInfoDTO();
        webInfoDTO.setNickname(nickname);
        webInfoDTO.setEmail(email);
        webInfoDTO.setDescription(description);
        webInfoDTO.setArticleCount(articleCount);
        webInfoDTO.setCommentCount(commentCount);
        webInfoDTO.setLastActive(Math.toIntExact(daysBetween));
        webInfoDTO.setRunTime(runTime);
        webInfoDTO.setSocialInfo(webInfo.getSocialInfo());
        webInfoDTO.setIcp(webInfo.getIcp());
        webInfoDTO.setPolice(webInfo.getPolice());
        webInfoDTO.setWebDescription(webInfo.getDescription());
        webInfoDTO.setKeywords(webInfo.getKeyword());
        webInfoDTO.setTitle(webInfo.getTitle());


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
