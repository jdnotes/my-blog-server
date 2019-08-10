package com.easy.blog.api.service.impl;

import com.easy.blog.api.dao.BlogArticleImagesMapper;
import com.easy.blog.api.service.BlogArticleImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhouyong
 * @date 2019/8/10
 */
@Service
public class BlogArticleImagesServiceImpl implements BlogArticleImagesService {

    @Autowired
    private BlogArticleImagesMapper blogArticleImagesMapper;

    @Override
    public String getUrlByCode(int code) {
        if (code <= 0) {
            return "";
        }
        return blogArticleImagesMapper.getUrlByCode(code);
    }
}