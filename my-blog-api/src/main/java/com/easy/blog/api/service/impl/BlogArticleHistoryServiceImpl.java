package com.easy.blog.api.service.impl;

import com.easy.blog.api.dao.BlogArticleHistoryMapper;
import com.easy.blog.api.model.BlogArticleHistory;
import com.easy.blog.api.service.BlogArticleHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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