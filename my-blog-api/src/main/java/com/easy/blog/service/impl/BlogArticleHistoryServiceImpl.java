package com.easy.blog.service.impl;

import com.easy.blog.dao.BlogArticleHistoryMapper;
import com.easy.blog.model.BlogArticleBack;
import com.easy.blog.model.BlogArticleHistory;
import com.easy.blog.service.BlogArticleHistoryService;
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
public class BlogArticleHistoryServiceImpl implements BlogArticleHistoryService {

    @Autowired
    private BlogArticleHistoryMapper blogArticleHistoryMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BlogArticleHistory param) {
        if (param == null) {
            throw new RuntimeException("blog article history param is null");
        }
        blogArticleHistoryMapper.insertSelective(param);
    }

}