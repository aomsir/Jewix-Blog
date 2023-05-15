package com.aomsir.jewixapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.ArticleMapper;
import com.aomsir.jewixapi.mapper.CommentMapper;
import com.aomsir.jewixapi.pojo.dto.CommentDTO;
import com.aomsir.jewixapi.pojo.entity.Article;
import com.aomsir.jewixapi.pojo.entity.Comment;
import com.aomsir.jewixapi.pojo.vo.CommentAddVo;
import com.aomsir.jewixapi.pojo.vo.CommentUpdateStatusVo;
import com.aomsir.jewixapi.pojo.vo.CommentUpdateVo;
import com.aomsir.jewixapi.service.CommentService;
import com.aomsir.jewixapi.util.NetUtils;
import com.aomsir.jewixapi.util.PageUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.aomsir.jewixapi.constant.ArticleConstants.ARTICLE_IS_NULL;
import static com.aomsir.jewixapi.constant.CommentConstants.*;
import static com.aomsir.jewixapi.constant.RedisConstants.WEB_CONFIG_KEY;

/**
 * @Author: Aomsir
 * @Date: 2023/3/16
 * @Description: 评论业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */
// TODO：部分业务采用递归实现
@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger log = LoggerFactory.getLogger(CommentServiceImpl.class);
    @Resource
    private CommentMapper commentMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private NetUtils netUtils;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public PageUtils searchBackendCommentListByPage(Map<String, Object> param) {
        Integer count = this.commentMapper.queryCommentBackendCount(param);

        ArrayList<Comment> list = null;
        if (count > 0) {
            list = this.commentMapper.queryCommentBackendPageList(param);
        } else {
            list = new ArrayList<>();
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        return new PageUtils(list,count,start,length);
    }


    @Override
    public PageUtils searchFrontCommentListByPage(Map<String, Object> param) {
        Integer count = this.commentMapper.queryCommentFrontParentCount(param);    // 查询的一级评论的数量

        ArrayList<Comment> list = null;
        List<CommentDTO> commentDTOList = null;
        if (count > 0) {
            list = this.commentMapper.queryCommentFrontPageList(param);     // 当前文章下所有的评论(已开放)
            commentDTOList = new ArrayList<>();


            // 抽出所有一级评论
            for (Comment comment : list) {
                if (comment.getParentId() == 0) {
                    commentDTOList.add(BeanUtil.copyProperties(comment, CommentDTO.class));
                }
            }

            // 封装所有的二级评论
            for (CommentDTO commentDTO : commentDTOList) {
                ArrayList<Comment> comments = new ArrayList<>();
                for (Comment comment : list) {
                    if (Objects.equals(comment.getPermId(), commentDTO.getId())) {
                        comments.add(comment);
                    }
                }
                commentDTO.setChildList(comments);
            }
        } else {
            commentDTOList = new ArrayList<>();
        }
        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(commentDTOList,count,start,length);
        return pageUtils;
    }



    @Override
    @Transactional
    public int addComment(CommentAddVo commentAddVo, HttpServletRequest request) throws JsonProcessingException {
        Map<String, Object> param = BeanUtil.beanToMap(commentAddVo);

        Long parentId = commentAddVo.getParentId();    // 父级评论id
        Long targetId = commentAddVo.getTargetId();    // 文章/页面id
        Long permId = commentAddVo.getPermId();        // 一级评论id


        // 处理目标文章/页面
        // 1:文章,21:时光机,22-友人帐,23-留言板,24-关于
        if (commentAddVo.getType() == 1) {
            Article article = this.articleMapper.queryArticleById(targetId);
            if (Objects.isNull(article)) {
                throw new CustomerException(ARTICLE_IS_NULL);
            }
            param.put("type",1);
        } else if (commentAddVo.getType() == 21) {
            // 时光机
            // TODO：校验是否是管理员
            param.put("type",21);
        } else if (commentAddVo.getType() == 23) {
            // 友人帐
            param.put("type",23);
        } else if (commentAddVo.getType() == 24) {
            // 留言板
            param.put("type",24);
        } else if (commentAddVo.getType() == 25) {
            // 通用
            param.put("type",25);
        } else {
            throw new CustomerException(COMMENT_TYPE_ERROR);
        }

        // 校验父级评论是否存在
        if (parentId != 0) {
            // 说明是回复评论
            // 查询父级评论
            Comment parentComment = this.commentMapper.queryCommentById(parentId);
            if (Objects.isNull(parentComment)) {
                throw new CustomerException(PARENT_COMMENT_IS_NULL);
            }
        }

        // 校验一级评论是否存在
        if (permId != 0) {
            Comment permComment = this.commentMapper.queryCommentById(permId);
            if (Objects.isNull(permComment)) {
                throw new CustomerException(FIRST_COMMENT_IS_NULL);
            }
        }


        // 处理ip，agent等信息
        String realIp = netUtils.getRealIp(request);
        String userAgent = request.getHeader("User-Agent");

        Map<String, String> userAgentMap = this.netUtils.parseUserAgent(userAgent);
        String userAgentString = new ObjectMapper().writeValueAsString(userAgentMap);    // 将userAgentMap转换为json
        String locationString = this.netUtils.getLocationInfo(realIp);

        param.put("ip",realIp);
        param.put("agent",userAgentString);
        param.put("location",locationString);

        param.put("status",0);    // 0-待审核
        param.put("createTime",new Date());
        param.put("updateTime",new Date());

        return this.commentMapper.insertComment(param);
    }

    @Override
    @Transactional
    public int updateComment(CommentUpdateVo commentUpdateVo) {
        Comment comment = this.commentMapper.queryCommentById(commentUpdateVo.getId());
        if (Objects.isNull(comment)) {
            throw new CustomerException(COMMENT_IS_NULL);
        }

        Map<String, Object> param = BeanUtil.beanToMap(commentUpdateVo);
        param.put("updateTime",new Date());

        // 删除缓存
        this.redisTemplate.delete(WEB_CONFIG_KEY);
        return this.commentMapper.updateComment(param);
    }


    @Override
    @Transactional
    public int deleteComment(List<Long> ids) {

        if (Objects.isNull(ids) || ids.size() == 0) {
            throw new CustomerException(COMMENT_DELETE_LIST_IS_NULL);
        }
        int role = 0;
        for (Long id : ids) {
            Comment comment = this.commentMapper.queryCommentById(id);
            if (Objects.isNull(comment)) {
                throw new CustomerException(COMMENT_IS_NULL);
            }

            // 查询是否有子评论
            // parentId与permId同为0则为一级评论
            List<Comment> childList_1 = this.commentMapper.queryCommentsByPermId(id);
            if (childList_1.size() > 0) {
                throw new CustomerException(COMMENT_HAS_SON);
            }
            List<Comment> childList_2 = this.commentMapper.queryCommentsByParentId(id);
            if (childList_2.size() > 0) {
                throw new CustomerException(COMMENT_HAS_SON);
            }

            role = this.commentMapper.deleteComment(id);
        }

        // 删除缓存
        this.redisTemplate.delete(WEB_CONFIG_KEY);
        return role;
    }

    @Override
    @Transactional
    public int updateCommentStatus(CommentUpdateStatusVo commentUpdateStatusVo) {
        Long id = commentUpdateStatusVo.getId();
        Integer status = commentUpdateStatusVo.getStatus();
        Comment comment = this.commentMapper.queryCommentById(id);
        if (Objects.isNull(comment)) {
            throw new CustomerException(COMMENT_IS_NULL);
        }

        Map<String, Object> param = new HashMap<>();
        param.put("id",id);
        param.put("status",status);
        param.put("updateTime",new Date());


        if (status == 1) {
            // 删除缓存
            this.redisTemplate.delete(WEB_CONFIG_KEY);
        }
        return this.commentMapper.updateCommentStatus(param);
    }
}
