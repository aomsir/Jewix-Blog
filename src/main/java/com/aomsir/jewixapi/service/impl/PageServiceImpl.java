package com.aomsir.jewixapi.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.aomsir.jewixapi.mapper.CommentMapper;
import com.aomsir.jewixapi.mapper.PageMapper;
import com.aomsir.jewixapi.pojo.entity.Page;
import com.aomsir.jewixapi.pojo.vo.PageAddVo;
import com.aomsir.jewixapi.pojo.vo.PageDeleteVo;
import com.aomsir.jewixapi.pojo.vo.PageUpdateVo;
import com.aomsir.jewixapi.service.PageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    @Override
    public List<Page> getPageList() {
        // TODO Auto-generated method stub
        return this.pageMapper.queryPageList();
    }

    @Override
    public Page getPageByOmit(Integer omit) {
        return this.pageMapper.queryPageByOmit(omit);
    }

    @Override
    @Transactional
    public int addPage(PageAddVo page) {
        Page page_1 = this.pageMapper.queryPageByOmit(page.getOmit());
        if (page_1 != null) {
            throw new RuntimeException("页面已存在");
        }

        Map<String, Object> param = BeanUtil.beanToMap(page);
        param.put("create_time", new Date());
        param.put("update_time", new Date());
        return this.pageMapper.insertPage(param);
    }

    @Override
    @Transactional
    public int deletePage(PageDeleteVo page) {
        Page page_1 = this.pageMapper.queryPageById(page.getId());
        if (page_1 == null) {
            throw new RuntimeException("页面不存在");
        }

        Integer type = 2;
        this.commentMapper.deleteCommentByPageId(page.getId(),type);   // 删除页面评论
        this.pageMapper.deletePageByIdAndOmit(page.getId(),page.getOmit());   // 删除页面
        return 0;
    }

    @Override
    @Transactional
    public int updatePage(PageUpdateVo page) {
        Page page_1 = this.pageMapper.queryPageById(page.getId());
        if (page_1 == null) {
            throw new RuntimeException("页面不存在");
        }

        Page page_2 = this.pageMapper.queryPageByOmit(page.getOmit());
        if (page_2 == null) {
            throw new RuntimeException("页面不存在");
        }

        Map<String, Object> param = BeanUtil.beanToMap(page);
        param.put("update_time", new Date());
        return this.pageMapper.updatePage(param);
    }
}
