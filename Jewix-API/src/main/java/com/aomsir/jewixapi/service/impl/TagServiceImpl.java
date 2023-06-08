package com.aomsir.jewixapi.service.impl;

import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.ArticleMapper;
import com.aomsir.jewixapi.mapper.TagMapper;
import com.aomsir.jewixapi.pojo.dto.ArticlePreviewDTO;
import com.aomsir.jewixapi.pojo.entity.Tag;
import com.aomsir.jewixapi.pojo.vo.TagUpdateVo;
import com.aomsir.jewixapi.service.TagService;
import com.aomsir.jewixapi.util.PageUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static com.aomsir.jewixapi.constant.TagConstants.TAG_HAS_EXISTED;
import static com.aomsir.jewixapi.constant.TagConstants.TAG_IS_NULL;


/**
 * @Author: Aomsir
 * @Date: 2023/2/21
 * @Description: 标签业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Service
public class TagServiceImpl implements TagService {

    private static final Logger log = LoggerFactory.getLogger(TagServiceImpl.class);
    @Resource
    private TagMapper tagMapper;


    @Resource
    private ArticleMapper articleMapper;

    @Override
    public PageUtils searchTagByPage(Map<String, Object> param) {
        Long count = this.tagMapper.queryTagCount();
        ArrayList<Tag> list = null;
        if (count > 0) {
            list = this.tagMapper.queryTagListByPage(param);
        } else {
            list = new ArrayList<>();
        }
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        return new PageUtils(list,count,start,length);
    }

    @Override
    @Transactional
    public int addTagByName(String tagName) {
        Tag respTag = this.tagMapper.queryTagByName(tagName);
        if (respTag != null) {
            throw new CustomerException(TAG_HAS_EXISTED);
        }

        Tag tag = new Tag();
        tag.setTagName(tagName);
        tag.setCreateTime(new Date());
        tag.setUpdateTime(new Date());
        return this.tagMapper.insertTag(tag);
    }


    @Override
    @Transactional
    public int updateTagById(TagUpdateVo updateVo) {
        Tag tag = this.tagMapper.queryTagById(updateVo.getId());
        if (tag == null) {
            throw new CustomerException(TAG_IS_NULL);
        }

        Tag tag_1 = this.tagMapper.queryTagByName(updateVo.getTagName());
        if (tag_1 != null) {
            throw new CustomerException(TAG_HAS_EXISTED);
        }

        tag.setTagName(updateVo.getTagName());
        tag.setUpdateTime(new Date());

        int role = this.tagMapper.updateTagById(tag);
        return role;
    }

    @Override
    public Tag searchTagById(Long tagId) {
        Tag tag = this.tagMapper.queryTagById(tagId);
        if (tag == null) {
            throw new CustomerException(TAG_IS_NULL);
        }
        return tag;
    }

    @Override
    public PageUtils searchArticleListByTagName(Map<String, Object> param) {
        Tag tag = this.tagMapper.queryTagByName((String) param.get("tagName"));
        if (tag == null) {
            throw new CustomerException(TAG_IS_NULL);
        }

        // 根据标签名查询有无可以返回的文章数
        Long count = this.articleMapper.queryArticleCountByTagName((String) param.get("tagName"));

        List<ArticlePreviewDTO> list = null;
        if (count > 0) {
            list = this.tagMapper.queryArticleListByTagName(param);
        } else {
            list = new ArrayList<>();
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        return new PageUtils(list,count,start,length);
    }
}