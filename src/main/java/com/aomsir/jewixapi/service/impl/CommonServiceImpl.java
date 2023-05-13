package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.mapper.*;
import com.aomsir.jewixapi.pojo.dto.ArticleArchiveInfoDTO;
import com.aomsir.jewixapi.pojo.dto.WebInfoDTO;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.service.CommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    @Override
    public WebInfoDTO searchWebInfo() {
        // TODO：查询Redis

        User user = this.userMapper.queryUserById(10000L);
        Map<String, Object> param = new HashMap<String, Object>();
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
        Long daysBetween = ChronoUnit.DAYS.between(date, today);
        WebInfoDTO webInfoDTO = new WebInfoDTO();
        webInfoDTO.setNickname(nickname);
        webInfoDTO.setEmail(email);
        webInfoDTO.setDescription(description);
        webInfoDTO.setArticleCount(articleCount);
        webInfoDTO.setCommentCount(commentCount);
        webInfoDTO.setLastActive(Math.toIntExact(daysBetween));


        // TODO:封装runTime
        // TODO：存入Redis
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
