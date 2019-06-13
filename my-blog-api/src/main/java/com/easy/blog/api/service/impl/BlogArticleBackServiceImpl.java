package com.easy.blog.api.service.impl;

import com.easy.blog.api.dao.BlogArticleBackMapper;
import com.easy.blog.api.model.BlogArticleBack;
import com.easy.blog.api.service.BlogArticleBackService;
import com.easy.blog.api.utils.RandomUtils;
import com.easy.blog.api.utils.SnowflakeIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zhouyong
 * @date 2019/6/9
 */
@Service
public class BlogArticleBackServiceImpl implements BlogArticleBackService {

    @Autowired
    private BlogArticleBackMapper blogArticleBackMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BlogArticleBack param) {
        if (param == null) {
            throw new RuntimeException("blog article back param is null");
        }
        param.setId(SnowflakeIdUtils.getSnowflakeId());
        if (param.getAuthorId() == null) {
            //默认站长
            param.setAuthorId(1L);
        }
        param.setCode(RandomUtils.getRandomStr(10));
        param.setReadNum(0);
        param.setLikeNum(0);
        param.setCreateDate(new Date());
        param.setUpdateDate(new Date());
        blogArticleBackMapper.insertSelective(param);
    }

    @Override
    public BlogArticleBack get(Long id) {
        return blogArticleBackMapper.get(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public String save(BlogArticleBack param) {
        if (param == null) {
            throw new RuntimeException("blog article back param is null");
        }
        Long id = null;
        if (param.getId() != null && param.getId() > 0) {
            BlogArticleBack old = this.get(param.getId());
            if (old != null) {
                id = param.getId();
                if (param.getAuthorId() == null) {
                    //默认站长
                    param.setAuthorId(1L);
                }
                param.setUpdateDate(new Date());
                blogArticleBackMapper.updateSelective(param);
            } else {
                this.add(param);
            }
        } else {
            id = SnowflakeIdUtils.getSnowflakeId();
            param.setId(id);
            if (param.getAuthorId() == null) {
                //默认站长
                param.setAuthorId(1L);
            }
            param.setCode(RandomUtils.getRandomStr(10));
            param.setReadNum(0);
            param.setLikeNum(0);
            param.setCreateDate(new Date());
            param.setUpdateDate(new Date());
            blogArticleBackMapper.insertSelective(param);
        }
        return String.valueOf(id);
    }
}