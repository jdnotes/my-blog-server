package com.easy.blog.service.impl;

import com.easy.blog.dao.BlogArticleBackMapper;
import com.easy.blog.model.BlogArticleBack;
import com.easy.blog.service.BlogArticleBackService;
import com.easy.blog.utils.RandomUtils;
import com.easy.blog.utils.SnowflakeIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhouyong
 * @date 2019/6/9
 */
@Service
public class BlogArticleBackServiceImpl implements BlogArticleBackService {

    @Autowired
    private BlogArticleBackMapper blogArticleBackMapper;

    @Override
    public void add(BlogArticleBack param) {
        if (param == null) {
            throw new RuntimeException("blog aritcle back param is null");
        }
        param.setId(SnowflakeIdUtils.getSnowflakeId());
        param.setAuthor("周永");
        param.setLevel(1);
        param.setStatus(0);
        param.setArticleType(2);
        param.setReadNum(RandomUtils.getRandomNum(10, 20));
        param.setLikeNum(RandomUtils.getRandomNum(10, 20));
        param.setCode(RandomUtils.getRandomStr(10));
        param.setCreateDate(new Date());
        param.setUpdateDate(new Date());
        blogArticleBackMapper.insertSelective(param);
    }
}