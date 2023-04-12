package com.aomsir.jewixapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.ArticleMapper;
import com.aomsir.jewixapi.mapper.CategoryMapper;
import com.aomsir.jewixapi.mapper.CommentMapper;
import com.aomsir.jewixapi.mapper.TagMapper;
import com.aomsir.jewixapi.pojo.dto.ArticleDetailDTO;
import com.aomsir.jewixapi.pojo.entity.Article;
import com.aomsir.jewixapi.pojo.vo.ArticleAddVo;
import com.aomsir.jewixapi.pojo.vo.ArticleUpdateVo;
import com.aomsir.jewixapi.service.ArticleService;
import com.aomsir.jewixapi.service.CommentService;
import com.aomsir.jewixapi.utils.HostHolder;
import com.aomsir.jewixapi.utils.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author: Aomsir
 * @Date: 2023/3/7
 * @Description: 文章业务实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Service
public class ArticleServiceImpl implements ArticleService {

    private static final Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);
    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private HostHolder hostHolder;

    @Resource
    private CommentMapper commentMapper;


    @Override
    public PageUtils searchBackendArticleListByPage(Map<String, Object> param) {
        int count = this.articleMapper.queryArticleBackendCount(param);
        ArrayList<Article> list = null;
        if (count > 0) {
            list = this.articleMapper.queryArticleBackendList(param);
        } else {
            list = new ArrayList<>();
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list,count,start,length);
        return pageUtils;
    }

    @Override
    public PageUtils searchFrontArticleListByPage(Map<String, Object> param) {
        int count = this.articleMapper.queryArticleFrontCount(param);
        ArrayList<Article> list = null;
        if (count > 0) {
            list = this.articleMapper.queryArticleFrontList(param);
        } else {
            list = new ArrayList<>();
        }


        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list,count,start,length);
        return pageUtils;
    }


    @Override
    @Transactional
    public int addArticle(ArticleAddVo articleAddVo) {
        if (articleAddVo.getType() == 2 && articleAddVo.getOriginUrl() == null) {
            throw new CustomerException("未填写原文链接");
        }

        // 查询分类id和标签id是否都存在
        List<Integer> categoryIds = articleAddVo.getCategoryIds();
        List<Integer> tagIds = articleAddVo.getTagIds();
        Boolean categoryFlag = this.categoryMapper.queryIdsExists(categoryIds);
        Boolean tagFlag = this.tagMapper.queryIdsExists(tagIds);

        int articleId = 0;

        // 存在则进行插入
        if (categoryFlag && tagFlag) {
            Map<String, Object> param = BeanUtil.beanToMap(articleAddVo);
            param.put("uuid", UUID.randomUUID().toString());
            param.put("createTime", new Date());
            param.put("updateTime", new Date());

            articleId = this.articleMapper.insertArticle(param);
            log.error("{}",articleId);

            // 文章正常插入再插入关联表
            if (articleId != 0) {

                // 遍历插入tb_tag_article
                for (Integer tagId : tagIds) {
                    this.articleMapper.insertArticleAndTag(articleId,tagId);
                }

                // 遍历tb_category_article
                for (Integer categoryId : categoryIds) {
                    this.articleMapper.insertArticleAndCategory(articleId,categoryId);
                }
            }

            // 插入article_user表
            this.articleMapper.insertArticleAndUser(articleId,this.hostHolder.getUserId());
        } else {
            throw new CustomerException("标签或分类不存在!");
        }
        return articleId > 0 ? 1 : 0;
    }


    @Override
    public int updateArticle(ArticleUpdateVo articleUpdateVo) {
        Long id = articleUpdateVo.getId();
        String uuid = articleUpdateVo.getUuid();
        Article article_1 = this.articleMapper.queryArticleByUuid(uuid);
        Article article_2 = this.articleMapper.queryArticleById(id);

        if (article_1 == null || article_2 == null ) {
            throw new CustomerException("文章不存在");
        }

        // TODO：处理article_tag与article_category

        Map<String, Object> param = BeanUtil.beanToMap(articleUpdateVo);
        param.put("updateTime", new Date());
        return this.articleMapper.updateArticle(param);
    }

    @Override
    public ArticleDetailDTO queryArticleByUuid(String uuid) {
        Article article = this.articleMapper.queryArticleByUuid(uuid);
        if (article == null) {
            throw new CustomerException("文章不存在");
        }

        // 数据复制
        ArticleDetailDTO articleDetailDTO= new ArticleDetailDTO();
        BeanUtil.copyProperties(article,articleDetailDTO);

        List<String> categotyList = this.articleMapper.queryArticleCategoryNameList(article.getId());
        List<String> tagList = this.articleMapper.queryArticleTagNameList(article.getId());
        String userName = this.articleMapper.queryArticleUserName(article.getId());    // 根据文章id获取文章的作者名

        articleDetailDTO.setCategories(categotyList);
        articleDetailDTO.setTags(tagList);
        articleDetailDTO.setUserName(userName);

        // 封装上一篇与下一篇文章uuid
        String lastUuid = this.articleMapper.queryLastUuidByCreateTime(article.getCreateTime());
        String nextUuid = this.articleMapper.queryNextUuidByCreateTime(article.getCreateTime());
        articleDetailDTO.setLastUuid(lastUuid);
        articleDetailDTO.setNextUuid(nextUuid);

        // TODO：评论数
        Integer count = this.categoryMapper.queryArticleCountsById(article.getId());
        articleDetailDTO.setCommentCount(count);
        return articleDetailDTO;
    }
}
