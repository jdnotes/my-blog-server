package com.easy.blog.service.impl;

import com.easy.blog.dao.BlogArticleBackMapper;
import com.easy.blog.model.BlogArticleBack;
import com.easy.blog.service.BlogArticleBackService;
import com.easy.blog.utils.RandomUtils;
import com.easy.blog.utils.SnowflakeIdUtils;
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
}