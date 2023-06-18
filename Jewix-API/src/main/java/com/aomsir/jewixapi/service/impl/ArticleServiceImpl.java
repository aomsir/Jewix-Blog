package com.aomsir.jewixapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.ArticleMapper;
import com.aomsir.jewixapi.mapper.CategoryMapper;
import com.aomsir.jewixapi.mapper.CommentMapper;
import com.aomsir.jewixapi.mapper.TagMapper;
import com.aomsir.jewixapi.pojo.dto.ArticleArchiveDTO;
import com.aomsir.jewixapi.pojo.dto.ArticleDetailDTO;
import com.aomsir.jewixapi.pojo.dto.ArticleRandomDTO;
import com.aomsir.jewixapi.pojo.entity.Article;
import com.aomsir.jewixapi.pojo.vo.ArticleAddVo;
import com.aomsir.jewixapi.pojo.vo.ArticleUpdateVo;
import com.aomsir.jewixapi.service.ArticleService;
import com.aomsir.jewixapi.util.UserHolder;
import com.aomsir.jewixapi.util.NetUtils;
import com.aomsir.jewixapi.util.PageUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.aomsir.jewixapi.constant.ArticleConstants.*;
import static com.aomsir.jewixapi.constant.CategoryConstants.CATEGORY_IS_NULL;
import static com.aomsir.jewixapi.constant.RedisConstants.*;

/**
 * @Author: Aomsir
 * @Date: 2023/3/7
 * @Description: 文章业务实体类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Service
public class ArticleServiceImpl implements ArticleService {

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private TagMapper tagMapper;

    @Resource
    private UserHolder userHolder;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private NetUtils netUtils;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;


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
        return new PageUtils(list,count,start,length);
    }

    @Override
    public PageUtils searchFrontArticleListByPage(Map<String, Object> param) {
        PageUtils pageUtils;
        if ((String) param.get("title") == null || ((String) param.get("title")).isEmpty()) {
            pageUtils= (PageUtils) this.redisTemplate.opsForValue()
                    .get(ARTICLE_FRONT_LIST_KEY);
            if (pageUtils != null && (int) param.get("start") == 0) {
                return pageUtils;
            }
        }

        int count = this.articleMapper.queryArticleFrontCount(param);
        ArrayList<Article> list = null;
        if (count > 0) {
            list = this.articleMapper.queryArticleFrontList(param);
        } else {
            list = new ArrayList<>();
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        pageUtils = new PageUtils(list, count, start, length);


        if ((String) param.get("title") == null || ((String) param.get("title")).isEmpty()) {
            if ((int) param.get("start") == 0) {
                this.redisTemplate.opsForValue()
                        .set(ARTICLE_FRONT_LIST_KEY, pageUtils, ARTICLE_FRONT_LIST_EXPIRE, TimeUnit.DAYS);
            }
        }

        return pageUtils;
    }


    @Override
    @Transactional
    public int addArticle(ArticleAddVo articleAddVo) {
        if (articleAddVo.getType() == 2 && articleAddVo.getOriginUrl() == null) {
            throw new CustomerException(ORIGIN_ADDRESS_IS_NULL);
        }

        // 查询分类id和标签id是否都存在
        List<Long> categoryIds = articleAddVo.getCategoryIds();
        List<Long> tagIds = articleAddVo.getTagIds();
        Boolean categoryFlag = this.categoryMapper.queryIdsExists(categoryIds);
        Boolean tagFlag = this.tagMapper.queryIdsExists(tagIds);

        Long articleId;

        // 存在则进行插入
        if (categoryFlag && tagFlag) {
            if (articleAddVo.getDescription() == null) {
                articleAddVo.setDescription("当前文章没有描述嗷~");
            }
            Map<String, Object> param = BeanUtil.beanToMap(articleAddVo);
            param.put("uuid", UUID.randomUUID().toString());
            param.put("createTime", new Date());
            param.put("updateTime", new Date());
            param.put("views", 0);

            this.articleMapper.insertArticle(param);
            BigInteger tempId = (BigInteger) param.get("id");
            articleId = tempId.longValue();

            // 文章正常插入再插入关联表
            if (articleId != 0) {

                // 遍历插入tb_tag_article
                for (Long tagId : tagIds) {
                    this.articleMapper.insertArticleAndTag(articleId,tagId);
                }

                // 遍历tb_category_article
                for (Long categoryId : categoryIds) {
                    this.articleMapper.insertArticleAndCategory(articleId,categoryId);
                }
            }

            // 插入article_user表
            this.articleMapper.insertArticleAndUser(articleId,this.userHolder.getUserId());
        } else {
            throw new CustomerException(CATEGORY_TAG_IS_NULL);
        }

        // 添加文章将list列表删除
        this.redisTemplate.delete(ARTICLE_FRONT_LIST_KEY);
        this.redisTemplate.delete(WEB_CONFIG_KEY);
        return articleId > 0 ? 1 : 0;
    }


    @Override
    @Transactional
    public int updateArticle(ArticleUpdateVo articleUpdateVo) {
        Long id = articleUpdateVo.getId();
        String uuid = articleUpdateVo.getUuid();
        Article article_1 = this.articleMapper.queryArticleByUuid(uuid);
        Article article_2 = this.articleMapper.queryArticleById(id);

        if (article_1 == null || article_2 == null ) {
            throw new CustomerException(ARTICLE_IS_NULL);
        }

        this.articleMapper.deleteArticleOfCategories(Collections.singletonList(articleUpdateVo.getId()));
        this.articleMapper.deleteArticleOfTags(Collections.singletonList(articleUpdateVo.getId()));

        Long articleId = articleUpdateVo.getId();
        for (Long categoryId : articleUpdateVo.getCategoryIds()) {
            if (this.categoryMapper.queryCategoryId(categoryId) != null) {
                this.articleMapper.insertArticleAndCategory(articleId,categoryId);
            } else {
                throw new CustomerException();
            }
        }

        for (Long tagId : articleUpdateVo.getTagIds()) {
            if (this.tagMapper.queryTagById(tagId) != null) {
                this.articleMapper.insertArticleAndTag(articleId,tagId);
            } else {
                throw new CustomerException(CATEGORY_IS_NULL);
            }
        }

        Map<String, Object> param = BeanUtil.beanToMap(articleUpdateVo);
        param.put("updateTime", new Date());

        // 删除当篇文章的Redis缓存以及List的缓存
        ArrayList<String> keys = new ArrayList<>();
        keys.add(ARTICLE_DETAIL_KEY + uuid);
        keys.add(ARTICLE_FRONT_LIST_KEY);
        keys.add(ARTICLE_RANDOM_KEY);
        keys.add(WEB_CONFIG_KEY);
        this.redisTemplate.delete(keys);

        return this.articleMapper.updateArticle(param);
    }

    @Override
    public ArticleDetailDTO queryArticleByUuid(String uuid, HttpServletRequest request) {
        ArticleDetailDTO detailDTO = (ArticleDetailDTO) this.redisTemplate.opsForValue()
                .get(ARTICLE_DETAIL_KEY + uuid);

        // 处理浏览量，防止刷浏览
        if (detailDTO != null) {
            this.displayViews(request, detailDTO.getUuid(), detailDTO.getId(), detailDTO.getViews(), detailDTO);
            return detailDTO;
        }

        // 查询可访问的文章
        Article article = this.articleMapper.queryArticleByUuid(uuid);
        if (article == null) {
            throw new CustomerException(ARTICLE_IS_NULL);
        }

        // 数据复制
        ArticleDetailDTO articleDetailDTO= new ArticleDetailDTO();
        BeanUtil.copyProperties(article,articleDetailDTO);

        // 查询文章所属标签、分类、用户名
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


        // 封装tagIds和categoryIds
        List<Long> tagIdList = this.articleMapper.queryArticleTagIdList(article.getId());
        List<Long> categoryIdList = this.articleMapper.queryArticleCategoryIdList(article.getId());
        articleDetailDTO.setTagIds(tagIdList);
        articleDetailDTO.setCategoryIds(categoryIdList);

        if (article.getViews() == null) {
            article.setViews(0);
        }

        Integer count = this.categoryMapper.queryArticleCommentCountsById(article.getId(),1);
        articleDetailDTO.setCommentCount(count);

        // 存储数据至Redis
        this.redisTemplate.opsForValue()
                .set(ARTICLE_DETAIL_KEY + article.getUuid(), articleDetailDTO, ARTICLE_DETAIL_EXPIRE,TimeUnit.DAYS);
        return articleDetailDTO;
    }

    @Override
    public ArticleDetailDTO queryArticleByUuid(String uuid) {
        // 查询文章
        Article article = this.articleMapper.queryBackendArticleByUuid(uuid);
        if (article == null) {
            throw new CustomerException(ARTICLE_IS_NULL);
        }

        // 数据复制
        ArticleDetailDTO articleDetailDTO= new ArticleDetailDTO();
        BeanUtil.copyProperties(article,articleDetailDTO);

        // 查询文章所属标签、分类、用户名
        List<String> categotyList = this.articleMapper.queryArticleCategoryNameList(article.getId());
        List<String> tagList = this.articleMapper.queryArticleTagNameList(article.getId());
        String userName = this.articleMapper.queryArticleUserName(article.getId());    // 根据文章id获取文章的作者名
        articleDetailDTO.setCategories(categotyList);
        articleDetailDTO.setTags(tagList);
        articleDetailDTO.setUserName(userName);


        // 封装tagIds和categoryIds
        List<Long> tagIdList = this.articleMapper.queryArticleTagIdList(article.getId());
        List<Long> categoryIdList = this.articleMapper.queryArticleCategoryIdList(article.getId());
        articleDetailDTO.setTagIds(tagIdList);
        articleDetailDTO.setCategoryIds(categoryIdList);

        return articleDetailDTO;
    }

    private void displayViews(HttpServletRequest request, String uuid2, Long id, Integer views, ArticleDetailDTO detailDTO) {
        String ip = this.netUtils.getRealIp(request);

        String viewIpKey = ARTICLE_VIEW_IP_KEY + ip + ":articleUuid:" + uuid2;
        String viewInfoKey = ARTICLE_VIEW_INFO_KEY + id;

        // 查询Redis,当前ip有无访问过当前文章
        Long isView = (Long) this.redisTemplate.opsForValue()
                .get(viewIpKey);
        if (isView == null || isView == 0) {

            // 未访问过,记录,值+1
            // 同IP 1天内刷新只算一条访问量
            this.redisTemplate.opsForValue()
                    .set(viewIpKey,1L,7, TimeUnit.DAYS);


            this.redisTemplate.opsForValue()
                    .increment(viewInfoKey + id);

            // TODO：完善成自动任务(下面代码)
            // this.articleMapper.updateArticleViewCount(id, views + 1);
        }
    }


    @Override
    @Transactional
    public int deleteArticleByArchive(List<Long> ids) {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            throw new CustomerException(ARTICLE_CHECKED);
        }

        int articleCounts = this.articleMapper.queryArticleCountById(ids);
        if (articleCounts != ids.size()) {
            throw new CustomerException(ARTICLE_LIST_ERROR);
        }

        int role = this.articleMapper.deleteArticlesByArchive(ids);
        if (role != ids.size()) {
            throw new CustomerException(ARTICLE_DELETE_ERROR);
        }

        // 简单处理，颗粒度不够细
        ArrayList<String> keys = new ArrayList<>();
        keys.add(ARTICLE_FRONT_LIST_KEY);
        keys.add(ARTICLE_RANDOM_KEY);
        keys.add(WEB_CONFIG_KEY);
        this.redisTemplate.delete(keys);
        this.redisTemplate.delete(Objects.requireNonNull(this.redisTemplate.keys("article:detail:*")));
        return role;
    }

    @Override
    @Transactional
    public int deleteArticleByPhysics(List<Long> ids) {
        if (Objects.isNull(ids) || ids.isEmpty()) {
            throw new CustomerException(ARTICLE_CHECKED);
        }

        // 确保选择的文章没有被别人删除(比如页面长时间没刷新)
        int articleCounts = this.articleMapper.queryArticleCountById(ids);
        if (articleCounts != ids.size()) {
            throw new CustomerException(ARTICLE_LIST_ERROR);
        }


        // 1、删除文章表中的数据(is_delete置1即可)
        int role = this.articleMapper.deleteArticlesByPhysics(ids);
        if (role != ids.size()) {
            throw new CustomerException(ARTICLE_DELETE_ERROR);
        }

        // 2、删除文章-分类表中的数据
        this.articleMapper.deleteArticleOfCategories(ids);

        // 3、删除文章-标签表中的数据
        this.articleMapper.deleteArticleOfTags(ids);

        // 4、删除文章-用户表中的数据
        this.articleMapper.deleteArticleOfUser(ids);

        // 删除Redis中的缓存
        ArrayList<String> keys = new ArrayList<>();
        keys.add(ARTICLE_FRONT_LIST_KEY);
        keys.add(ARTICLE_RANDOM_KEY);
        keys.add(WEB_CONFIG_KEY);
        this.redisTemplate.delete(keys);
        this.redisTemplate.delete(Objects.requireNonNull(this.redisTemplate.keys("article:detail:*")));
        return role;
    }


    @Override
    public List<ArticleRandomDTO> searchRandomArticle() {
        List<ArticleRandomDTO> resultList = (List<ArticleRandomDTO>)this.redisTemplate.opsForValue()
                .get(ARTICLE_RANDOM_KEY);
        if (resultList != null && !resultList.isEmpty()) {
            return resultList;
        }

        List<Long> articleIds = this.articleMapper.queryArticleId();
        int size;
        List<Long> randomList;
        if (articleIds.size() >= 5) {
            size = 5;
            Collections.shuffle(articleIds);   // 对list进行随机化
            randomList = articleIds.subList(0, size);
        } else {
            randomList = articleIds;
        }


        resultList = this.articleMapper.queryArticlesByRandomIds(randomList);
        if (resultList != null) {
            // 存储至Redis
            this.redisTemplate.opsForValue()
                    .set(ARTICLE_RANDOM_KEY, resultList, ARTICLE_RANDOM_EXPIRE, TimeUnit.DAYS);
            return resultList;
        }

        return new ArrayList<>(0);
    }

    @Override
    public PageUtils searchArticlesByArchive(Map<String, Object> param) {
        Integer articleCount = this.articleMapper.queryArticleCountByArchive();
        ArrayList<ArticleArchiveDTO> list = null;
        if (articleCount > 0) {
            list = this.articleMapper.queryArticleByArchive(param);
        } else {
            list = null;
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        return new PageUtils(list,articleCount,start,length);
    }
}
