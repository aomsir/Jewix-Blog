package com.aomsir.jewixapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.exception.CustomerException;
import com.aomsir.jewixapi.mapper.CommentMapper;
import com.aomsir.jewixapi.mapper.PageMapper;
import com.aomsir.jewixapi.mapper.UserMapper;
import com.aomsir.jewixapi.pojo.dto.PageListDTO;
import com.aomsir.jewixapi.pojo.entity.Page;
import com.aomsir.jewixapi.pojo.entity.User;
import com.aomsir.jewixapi.pojo.vo.PageAddVo;
import com.aomsir.jewixapi.pojo.vo.PageUpdateVo;
import com.aomsir.jewixapi.service.PageService;
import com.aomsir.jewixapi.util.UserHolder;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.aomsir.jewixapi.constant.PageConstants.*;
import static com.aomsir.jewixapi.constant.RedisConstants.PAGE_LIST_EXPIRE;
import static com.aomsir.jewixapi.constant.RedisConstants.PAGE_LIST_KEY;

/**
 * @Author: Aomsir
 * @Date: 2023/3/21
 * @Description: 页面服务实现类
 * @Email: info@say521.cn
 * @GitHub: <a href="https://github.com/aomsir">GitHub</a>
 */

@Service
public class PageServiceImpl implements PageService {

    @Resource
    private PageMapper pageMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserHolder userHolder;

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<PageListDTO> searchPageList() {
        // 缓存命中
        List<PageListDTO> pageListDTOS = (List<PageListDTO>) this.redisTemplate.opsForValue().get(PAGE_LIST_KEY);
        if (pageListDTOS != null) {
            return pageListDTOS;
        }

        List<PageListDTO> pageList = this.pageMapper.queryPageList();
        // 封装用户名(尽量减少查询次数)
        List<Long> userIds = this.pageMapper.queryPageUserIds();
        Map<Long,String> idAndNameMap = new HashMap<>();
        for (Long userId : userIds) {
            User user = this.userMapper.queryUserById(userId);
            if (user != null) {
                idAndNameMap.put(userId,user.getNickname());
            }
        }

        for (PageListDTO pageListDTO : pageList) {
            Long userId = pageListDTO.getUserId();
            if (userId != null) {
                pageListDTO.setUserName(idAndNameMap.get(userId));
            } else {
                pageListDTO.setUserName("无名氏");
            }
        }

        // 封装数据进Redis
        this.redisTemplate.opsForValue()
                .set(PAGE_LIST_KEY, pageList, PAGE_LIST_EXPIRE, TimeUnit.DAYS);
        return pageList;
    }

    @Override
    public Page searchPageByUuid(String uuid) {
        Page page = this.pageMapper.queryPageByUuid(uuid);
        if (page == null) {
            throw new CustomerException(PAGE_IS_NULL);
        }

        User user = this.userMapper.queryUserById(page.getUserId());
        if (user == null) {
            page.setUserName("无名氏");
        } else {
            page.setUserName(user.getNickname());
        }

        return page;
    }

    @Override
    @Transactional
    public int addPage(PageAddVo pageAddVo) {
        // 如果是1-4,校验一下
        Set<Integer> containKey = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        Integer type = pageAddVo.getType();
        if (containKey.contains(type)) {
            Page page = this.pageMapper.queryPageByType(type);
            if (page != null) {
                throw new CustomerException(PAGE_HAS_EXISTED);
            }
        }

        if (this.pageMapper.queryPageByOmit(pageAddVo.getOmit()) != null) {
            throw new CustomerException(PAGE_HAS_EXISTED);
        }

        Page page = this.pageMapper.queryPageByTitle(pageAddVo.getTitle());
        if (page != null) {
            throw new CustomerException(PAGE_HAS_EXISTED);
        }

        Page newPage = new Page();
        BeanUtil.copyProperties(pageAddVo,newPage);
        newPage.setUuid(UUID.randomUUID().toString());
        newPage.setUserId(this.userHolder.getUserId());
        newPage.setViews(0L);
        newPage.setCreateTime(new Date());
        newPage.setUpdateTime(new Date());

        // 删除缓存
        this.redisTemplate.delete(PAGE_LIST_KEY);
        return this.pageMapper.insertPage(newPage);
    }


    @Override
    @Transactional
    public int updatePage(PageUpdateVo pageUpdateVo) {
        String title = pageUpdateVo.getTitle();
        Page page = this.pageMapper.queryPageByTitle(title);
        if (page != null && !Objects.equals(page.getId(), pageUpdateVo.getId())) {
            throw new CustomerException(PAGE_TITLE_HAS_EXISTED);
        }

        Page page1 = this.pageMapper.queryPageByOmit(pageUpdateVo.getOmit());
        if (page1 != null && !Objects.equals(page1.getOmit(), pageUpdateVo.getOmit())) {
            throw new CustomerException(PAGE_OMIT_HAS_EXISTED);
        }

        // 超级管理员和创建者可以管理
        Page page_1 = this.pageMapper.queryPageByUuid(pageUpdateVo.getUuid());
        if (page_1 != null && !Objects.equals(page_1.getUserId(), this.userHolder.getUserId()) || this.userHolder.getUserId() != 10000L) {
            throw new CustomerException(PAGE_IS_NOT_MINE);
        }

        Page page_2 = new Page();
        BeanUtil.copyProperties(pageUpdateVo,page_2);
        page_2.setUpdateTime(new Date());

        // 删除缓存
        this.redisTemplate.delete(PAGE_LIST_KEY);
        return this.pageMapper.updatePage(page_2);
    }

    @Override
    @Transactional
    public int deletePage(String uuid) {
        if (uuid == null) {
            throw new CustomerException("uuid为空");
        }

        Page page = this.pageMapper.queryPageByUuid(uuid);
        if (page == null) {
            throw new CustomerException(PAGE_IS_NULL);
        }

        int role = this.pageMapper.deletePage(uuid);
        if (role > 0) {
            // 删除评论
            List<Long> commentIds = this.commentMapper.queryCommentIdsByTypeAndTargetId(page.getId(),20+page.getType());
            if (commentIds != null && commentIds.size() > 0) {
                this.commentMapper.deleteCommentByIds(commentIds);
            }
        }

        // 缓存
        this.redisTemplate.delete(PAGE_LIST_KEY);
        return role;
    }
}
