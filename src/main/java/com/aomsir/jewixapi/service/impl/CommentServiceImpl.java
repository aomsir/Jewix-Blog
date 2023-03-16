package com.aomsir.jewixapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.mapper.CommentMapper;
import com.aomsir.jewixapi.pojo.dto.CommentDTO;
import com.aomsir.jewixapi.pojo.entity.Comment;
import com.aomsir.jewixapi.service.CommentService;
import com.aomsir.jewixapi.utils.PageUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: Aomsir
 * @Date: 2023/3/16
 * @Description: 评论业务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Resource
    private CommentMapper commentMapper;

    @Override
    public PageUtils searchBackendCommentListByPage(Map<String, Object> param) {
        int count = this.commentMapper.queryCommentBackendCount(param);

        ArrayList<Comment> list = null;
        if (count > 0) {
            list = this.commentMapper.queryCommentBackendPageList(param);
        } else {
            list = new ArrayList<>();
        }

        int start = (Integer) param.get("start");
        int length = (Integer) param.get("length");
        PageUtils pageUtils = new PageUtils(list,count,start,length);
        return pageUtils;
    }


    @Override
    public PageUtils searchFrontCommentListByPage(Map<String, Object> param) {
        int count = this.commentMapper.queryCommentFrontParentCount(param);    // 查询的一级评论的数量

        ArrayList<Comment> list = null;
        List<CommentDTO> commentDTOList = null;
        if (count > 0) {
            list = this.commentMapper.queryCommentFrontPageList(param);     // 当前文章下所有的评论
            commentDTOList = new ArrayList<>();

            // TODO：封装二级评论列表
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
                    if (Objects.equals(comment.getParentId(), commentDTO.getId())) {
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
}
