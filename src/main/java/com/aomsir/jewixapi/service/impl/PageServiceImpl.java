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
import com.aomsir.jewixapi.pojo.vo.PageDeleteVo;
import com.aomsir.jewixapi.pojo.vo.PageUpdateVo;
import com.aomsir.jewixapi.service.PageService;
import com.aomsir.jewixapi.utils.HostHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

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
    private HostHolder hostHolder;

    @Override
    public List<PageListDTO> searchPageList() {
        List< PageListDTO> pageList = this.pageMapper.queryPageList();

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

        // TODO:封装进Redis
        return pageList;
    }

    @Override
    public Page searchPageByUuid(String uuid) {
        Page page = this.pageMapper.queryPageByUuid(uuid);
        if (page == null) {
            throw new CustomerException("页面不存在");
        }

        User user = this.userMapper.queryUserById(page.getUserId());
        if (user == null) {
            page.setUserName("无名氏");
        } else {
            page.setUserName(user.getNickname());
        }

        // TODO: 存入Redis
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
                throw new CustomerException("页面已存在");
            }
        }

        if (this.pageMapper.queryPageByOmit(pageAddVo.getOmit()) != null) {
            throw new CustomerException("页面已经存在");
        }

        Page page = this.pageMapper.queryPageByTitle(pageAddVo.getTitle());
        if (page != null) {
            throw new CustomerException("页面已存在");
        }

        Page newPage = new Page();
        BeanUtil.copyProperties(pageAddVo,newPage);
        newPage.setUuid(UUID.randomUUID().toString());
        newPage.setUserId(this.hostHolder.getUserId());
        newPage.setViews(0L);
        newPage.setCreateTime(new Date());
        newPage.setUpdateTime(new Date());

        return this.pageMapper.insertPage(newPage);
    }


    @Override
    @Transactional
    public int updatePage(PageUpdateVo pageUpdateVo) {
        String title = pageUpdateVo.getTitle();
        Page page = this.pageMapper.queryPageByTitle(title);
        if (!Objects.equals(page.getId(), pageUpdateVo.getId())) {
            throw new CustomerException("页面标题已存在");
        }

        Page page1 = this.pageMapper.queryPageByOmit(pageUpdateVo.getOmit());
        if (!Objects.equals(page.getOmit(), pageUpdateVo.getOmit())) {
            throw new CustomerException("路径名已经存在");
        }

        // 超级管理员和创建者可以管理
        Page page_1 = this.pageMapper.queryPageByUuid(pageUpdateVo.getUuid());
        if (!Objects.equals(page_1.getUserId(), this.hostHolder.getUserId()) || this.hostHolder.getUserId() != 10000L) {
            throw new CustomerException("非当前用户创建,无法修改");
        }

        Page page_2 = new Page();
        BeanUtil.copyProperties(pageUpdateVo,page_2);
        page_2.setUpdateTime(new Date());

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
            throw new CustomerException("页面不存在,无法删除");
        }

        int role = this.pageMapper.deletePage(uuid);
        if (role > 0) {
            // 删除评论
            List<Long> commentIds = this.commentMapper.queryCommentIdsByTypeAndTargetId(page.getId(),20+page.getType());
            this.commentMapper.deleteCommentByIds(commentIds);
        }
        return role;
    }
}
