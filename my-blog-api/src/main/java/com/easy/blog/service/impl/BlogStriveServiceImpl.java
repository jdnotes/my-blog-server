package com.easy.blog.service.impl;

import com.easy.blog.dao.BlogStriveMapper;
import com.easy.blog.model.BlogStrive;
import com.easy.blog.service.BlogStriveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhouyong
 * @date 2019/6/6
 */
@Service
public class BlogStriveServiceImpl implements BlogStriveService {

    @Autowired
    private BlogStriveMapper blogStriveMapper;

    @Override
    public BlogStrive get(Long id) {
        return blogStriveMapper.get(id);
    }
}